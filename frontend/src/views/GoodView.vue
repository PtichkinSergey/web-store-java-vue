<template>
    <div class="page">
        <Toolbar/>
        <div v-if="$store.state.goods.length > 0">
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
                    <p>{{ this.good.cost }} руб.</p>
                    <v-btn
                        @click="this.addToBasket(good)"
                        class="add_to_basket_btn"
                        :disabled="good.count < 1"
                    >
                        В корзину
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
    export default {
        name: 'GoodView',
        data: () => ({
            good: {}
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
            this.good = this.$store.state.goods.find((good) => good.id == this.$route.params.id );
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