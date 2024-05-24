<template>
    <div>
        <Toolbar/>
        <div v-if="basket.length > 0">
            <h3 class="page_title">Корзина</h3>
        </div>
        <div 
            v-if="basket.length > 0"
            id="basket"
        >
            <v-list 
                class="list"
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
                        <div id="image">
                            <v-img 
                                src="https://www.ulfven.no/files/sculptor30/library/images/default-product-image.png"
                            />
                        </div>
                        <div id="good_name">
                            <h3>{{ basket_good.good.name }}</h3>
                        </div>
                        <div id="amount_chooser">
                            <v-btn @click="this.decreaseGoodCount(basket_good.good.id)">
                                <v-icon>mdi-minus</v-icon>
                            </v-btn>
                            <p>{{ basket_good.count_in_basket }}</p>
                            <v-btn @click="this.increaseGoodCount(basket_good.good.id)">
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
                <p>Всего товаров: {{ this.$store.getters.getGoodsTotalCount }}</p>
                <p>Итого: {{ orderSum(basket.filter((good) => good.selected)) }} руб.</p>
                <v-btn>Оформить заказ</v-btn>
            </div>
        </div>
        
        <div v-else>
            <h3>Корзина пустая!</h3>
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
            increaseGoodCount(id) {
                this.$store.commit('increaseBasketGoodCount', id);
            },
            decreaseGoodCount(id) {
                this.$store.commit('decreaseBasketGoodCount', id);
            },
            orderSum(basket_goods) {
                let sum = 0;
                for(let basket_good of basket_goods) {
                    sum += basket_good.good.cost * basket_good.count_in_basket;
                }
                return sum;
            }
        },
        mounted() {
            this.fetchBasket();
        }
    }
</script>

<style lang="scss" scoped>
#basket_card {
    display: flex;
    align-items: center;
    justify-content: space-between;
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
    width: 10vw;
}
#basket_list {
    width: 80vw;
}
#cost {
    width: 5vw;
}
</style>