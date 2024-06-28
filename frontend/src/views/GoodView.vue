<template>
    <div class="page">
        <Toolbar/>
        <div v-if="good">
            <v-card class="card" id="good_card">
                <div id="card_left">
                    <h3
                        id="good_title"
                    >
                        {{ this.good.name }}
                    </h3>
                    <v-img 
                        :src="getImgUrl(this.good.image_path)"
                    />
                </div>
                <div id="card_right">
                    <div id="description_top">
                        <p>{{ this.good.description }}</p>
                    </div>
                    <div>
                        <p>Производитель: {{ this.good.manufacturer }}</p>
                    </div>
                    
                    <div id="description_bottom">
                        <p v-if="good.count > 0">
                            В наличии: {{ this.good.count }}
                        </p>
                        <p v-else>
                            Нет в наличии
                        </p>
                        <div
                            id="cost_info"
                            v-if="$store.state.auth_email"
                        >
                            <p>{{good.cost * (1 - good.discount)}} руб.</p>
                            <s id="cost_with_discount">{{good.cost}} руб.</s>
                        </div>
                        <p v-else>{{good.cost}} руб.</p>
                        <v-btn
                            v-if="$store.getters.getBasketContainsGood(this.good.id)"
                            id="basket_forward_btn"
                            @click="this.$router.push('/basket')"
                            variant="outlined"
                        >
                            В корзине
                        </v-btn>
                        <v-btn
                            v-else
                            @click="this.addToBasket(good)"
                            id="add_to_basket_btn"
                            :disabled="good.count < 1"
                        >
                            Купить
                        </v-btn> 
                    </div>
                </div>
             </v-card>
            <CommentList
                :good_id="good.id"
            />
        </div>
        <div v-else>
            <h3>Загрузка...</h3>
        </div>
    </div>
</template>

<script>
    import Toolbar from '@/components/Toolbar.vue'
    import CommentList from "@/components/CommentList.vue"
    import axios from 'axios'
    export default {
        name: 'GoodView',
        data: () => ({
            good: null,
        }),
        components: {
            Toolbar, CommentList
        },
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
            if(this.$store.state.goods.length > 0){
                this.good = this.$store.state.goods.find((good) => good.id == this.$route.params.id );
                return;
            }
            const baseURL = "http://localhost:5000/api/goods/" + this.$route.params.id;
            let headers = {Authorization: ''};
            if(this.$store.state.jwt) {
                headers.Authorization = 'Bearer ' + this.$store.state.jwt;
            }
            axios.get(baseURL, { headers: headers})
            .then(response => {
                this.good = {
                    id: response.data.id,
                    name: response.data.name,
                    cost: response.data.cost,
                    discount: 0.05,
                    count: response.data.count,
                    manufacturer: response.data.manufacturer,
                    categories: response.data.categories,
                    description: response.data.description,
                    image_path: response.data.imagePath
                }
            })
            .catch(e => {
                console.log(e); 
            });
        }
    }
</script>

<style lang="scss" scoped>
.page {
    text-align: center;
}
#good_title {
    text-align: left;
}
#good_card {
    display: flex;
    width: 60vw;
}
#card_left {
    width: 50%;
}
#card_right {
    margin-left: 20px;
    text-align: left;
    display: flex;
    flex-direction: column;
    width: 50%;
}
#description_bottom {
    display: flex;
    align-items: center;
    justify-content: space-around;
}
#description_top {
    margin-top: 20px;
}
</style>