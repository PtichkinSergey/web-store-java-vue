<template>
    <v-list 
        id="good_list"
        v-if="$store.state.goods.length > 0"
    >
        <v-list-item
            v-for="good of $store.state.goods"
            :key="good.id"
        >
            <div class="good_list_item">
                <div id="image">
                    <v-img 
                        :src="getImgUrl(good.image_path)"
                    />
                </div>
                <div id="description">
                    <h2
                        @click="this.$router.push(`/goods/${good.id}`);"
                        class="header_link"
                    >
                        {{good.name}}
                    </h2>
                    <p>{{good.description}}</p>
                </div>
                <div id="availability">
                    <p v-if="good.count > 5">В наличии</p>
                    <p v-else-if="good.count == 0">Нет в наличии</p>
                    <p v-else>Мало</p>
                </div>
                <div id="cost">
                    <div
                        id="cost_info"
                        v-if="$store.state.auth_email"
                    >
                        <p>{{good.cost * (1 - good.discount)}} руб.</p>
                        <s id="cost_with_discount">{{good.cost}} руб.</s>
                    </div>
                    <p v-else>{{good.cost}} руб.</p>
                    <v-btn
                        v-if="$store.getters.getBasketContainsGood(good.id)"
                        id="basket_forward_btn"
                        @click="this.$router.push('/basket')"
                        variant="outlined"
                    >
                        В корзине
                    </v-btn>
                    <v-btn
                        v-else
                        @click="addToBasket(good)"
                        id="add_to_basket_btn"
                        :disabled="good.count < 1"
                    >
                        Купить
                    </v-btn> 
                </div>
                
            </div>
        </v-list-item>
    </v-list>
    <div v-else>
        <h3>Товары отсутствуют...</h3>
    </div>
</template>

<script>
export default {
    name: "GoodList",
    data: () => ({
        
    }),
    methods: {
        async fetchGoods() {
            let category = 0;
            if(this.$route.query.category) {
                category = this.$route.query.category;
            }
            this.$store.dispatch('fetchGoods', category);
        },
        addToBasket(good) {
            this.$store.commit('addGoodToBasket', good);
        },
        getImgUrl(img) { 
            if(img){
                return require('@/assets/images/' + img);
            }
            return null;
        }
    },
    mounted() {
        this.fetchGoods();
    },
}
</script>

<style lang="scss" scoped>
#good_list {
    background-color: $main_bcg_color;
}
.good_list_item {
    display: flex;
    align-items: center;
    justify-content: space-around;
    background-color: white;
    width: 80vw;
    height: 20vh;
    border-radius: 25px;
}
#description {
    width: 25vw
}
#availability {
    width: 5vw;
}
#image {
    width: 12vw;
    height: 8vw;
}
#cost {
    width: 10vw;
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>