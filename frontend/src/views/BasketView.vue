<template>
    <div class="page">
        <Toolbar/>
        <div v-if="basket.length > 0">
            <h3 class="page_title">Корзина</h3>
        </div>
        <div v-else>
            <h2 class="empty_page_header">Корзина пустая!</h2>
        </div>
        <div 
            v-if="basket.length > 0"
            id="basket"
        >
            <v-list 
                id="basket_list"
            >
                <v-list-item
                    v-for="basket_good of basket"
                    :key="basket_good.good.id"
                >
                    <div
                        class="card" id="basket_card"
                    >
                        <div>
                            <v-checkbox
                                v-model="basket_good.selected"
                            ></v-checkbox>
                        </div>
                        <div>
                            <v-img 
                                id="image"
                                :src="getImgUrl(basket_good.image_path)"
                            />
                        </div>
                        <div id="good_name">
                            <h3>{{ basket_good.good.name }}</h3>
                        </div>
                        <div id="amount_chooser">
                            <v-btn @click="decreaseGoodCount(basket_good.good.id)">
                                <v-icon>mdi-minus</v-icon>
                            </v-btn>
                            <p>{{ basket_good.count_in_basket }}</p>
                            <v-btn @click="increaseGoodCount(basket_good.good.id)">
                                <v-icon>mdi-plus</v-icon>
                            </v-btn>
                        </div>
                        <div id="cost">
                            <p>{{ basket_good.good.cost }} руб.</p>
                        </div>
                    </div>
                </v-list-item>
            </v-list>
            <div class="card" id="summary">
                <h3>Ваш заказ:</h3>
                <p>Всего товаров: {{ basketTotalSize }}</p>
                <p>Итого: {{ totalCost }} руб.</p>
                <v-btn>Оформить заказ</v-btn>
            </div>
        </div>
    </div>
</template>

<script>
    import Toolbar from '@/components/Toolbar.vue'
    export default {
        components: {
            Toolbar
        },
        name: 'basket',
        data: () => ({
            basket: []
        }),
        methods: {
            fetchBasket() {
                this.basket = this.$store.state.basket;
            },
            removeGood(id) {
                this.basket = this.basket.filter((basket_good) => basket_good.good.id != id);
                if(this.basket.length < 1) {
                    localStorage.removeItem('basket');
                }
                else {
                    localStorage.setItem('basket', JSON.stringify(this.basket));
                }
            },
            increaseGoodCount(id) {
                for(let basket_good of this.basket){
                    if(basket_good.good.id == id) {
                        basket_good.count_in_basket ++;
                        localStorage.setItem('basket', JSON.stringify(this.basket));
                        return;
                    }
                }
            },
            decreaseGoodCount(id) {
                for(let basket_good of this.basket){
                    if(basket_good.good.id == id) {
                        basket_good.count_in_basket --;
                        if(basket_good.count_in_basket < 1) {
                            this.removeGood(id);
                        }
                        localStorage.setItem('basket', JSON.stringify(this.basket));
                        return;
                    }
                }
            }
        },
        computed: {
            totalCost() {
                let cost = 0;
                for(let basket_good of this.basket) {
                    if(basket_good.selected) {
                        cost += basket_good.good.cost * basket_good.count_in_basket;
                    }
                }
                return cost;
            },
            basketTotalSize() {
                let totalCount = 0;
                for(let basket_good of this.basket) {
                    if(basket_good.selected) {
                        totalCount += basket_good.count_in_basket;
                    }
                }
                return totalCount;
            },
            getImgUrl(img) { 
                if(img){
                    return require('@/assets/images/' + img);
                }
            }
        },
        watch: {

        },
        mounted() {
            this.fetchBasket();
        },
        unmounted() {
            this.$store.commit('setBasketData', this.basket);
        }
    }
</script>

<style lang="scss" scoped>
#basket_card {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

#basket_list {
    width: 70vw;
    background-color: $main_bcg_color;
}

#amount_chooser {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 8vw;
}
#image {
    width: 5vw;
    height: 5vw;
}
#good_name {
    width: 15vw;
}
#basket {
    display: flex;
    justify-content: space-between;
}
#summary {
    display: flex;
    flex-direction: column;
    text-align: left;
    width: 15vw;
}

#cost {
    width: 5vw;
}
</style>