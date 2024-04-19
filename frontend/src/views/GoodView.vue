<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card" id="good_card">
            <div id="image">
                <v-img 
                    src="https://www.ulfven.no/files/sculptor30/library/images/default-product-image.png"
                />
            </div>
            <div id="characteristics">
                <h3>{{ this.good.name }}</h3>
                <div>

                </div>
                <div>
                    <p>{{ this.good.description }}</p>
                </div>
                <div id="cost">
                    <p>{{ this.good.cost }} руб.</p>
                    <v-btn
                        @click="addToBasket(good.id)"
                        class="add_to_basket_btn"
                        :disabled="good.count < 1"
                    >
                        В корзину
                    </v-btn> 
                </div>
            </div>
        </v-card>
        <CommentList/>
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
    mounted() {
      this.good = this.$store.state.goods.find((good) => good.id == this.$route.params.id );
    }
  }
</script>

<style lang="scss" scoped>
.page {
  text-align: center;
}
#good_card {
  display: flex;
  width: 60vw;
}
#image {
  width: 30vw;
}
#characteristics {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 50%;
}
#cost {
  display: flex;
  align-self: flex-end;
  align-items: center;
}
</style>