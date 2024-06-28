<template>
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
                        :src= getImgUrl(basket_good.image_path)
                    />
                </div>
                <div id="good_name">
                    <h3>{{ basket_good.good.name }}</h3>
                </div>
                <div id="good_controls">
                    <div id="amount_chooser">
                        <v-btn 
                            @click="decreaseGoodCount(basket_good.good.id)"
                            icon="mdi-minus"
                            density="compact"
                        >
                        </v-btn>
                        <p>{{ basket_good.count_in_basket }}</p>
                        <v-btn 
                            @click="increaseGoodCount(basket_good.good.id)"
                            icon="mdi-plus"
                            density="compact"
                        >
                        </v-btn>
                    </div>
                    <a 
                        @click="removeGood(basket_good.good.id)"
                        class="link"
                    >
                        Удалить
                    </a>
                </div>

                <div id="cost">
                    <p>{{ basket_good.good.cost }} руб.</p>
                </div>
            </div>
        </v-list-item>
    </v-list>
</template>

<script>
    export default {
        data: () => ({
            basket: []
        }),
        methods: {
            fetchBasket() {
                this.basket = this.$store.state.basket;
            },
            removeGood(id) {
                this.basket = this.basket.filter((basket_good) => basket_good.good.id != id);
                this.$store.commit('removeGoodFromBasket', id);
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
                        else {
                            localStorage.setItem('basket', JSON.stringify(this.basket));
                        }
                        return;
                    }
                }
            },
            getImgUrl(img) { 
                if(img){
                    return './assets/images/' + img;
                }
                return null;
            }
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
#amount_chooser {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 5vw;
}
#image {
    width: 5vw;
    height: 5vw;
}
#good_name {
    width: 20vw;
}
#good_controls {
    display: flex;
    flex-direction: column;
    align-items: center;
}
#cost {
    width: 5vw;
}
</style>