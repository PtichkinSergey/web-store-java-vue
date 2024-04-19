<template>
    <div class="page">
        <h3>Корзина</h3>
        <v-list 
            class="list"
            v-if="basket.length > 0"
        >
            <v-list-item
                v-for="basket_good of basket"
                :key="basket_good.good.id"
            >
                <v-card
                    class="card" id="basket_card"
                >
                <div>
                    <v-checkbox
                        v-model="selected"
                        label="John"
                        value="John"
                    ></v-checkbox>
                </div>
                <div id="image">
                    
                </div>
                <div>
                    <h3>{{ basket_good.good.name }}</h3>
                </div>
                <div id="amount_choser">
                    <p>{{ basket_good.amount }}</p>
                </div>
                <div id="cost">
                    <p>{{ addToBasket.good.cost }}</p>
                </div>
                </v-card>
            </v-list-item>
        </v-list>
        <div v-else>
            <h3>Корзина пустая!</h3>
        </div>
    </div>
</template>

<script>
    export default {
        data: () => ({
            basket: []
        }),
        methods: {
            fetchBasket() {
                this.basket = this.$store.state.basket;
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
}
</style>