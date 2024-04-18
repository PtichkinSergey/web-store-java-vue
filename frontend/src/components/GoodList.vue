<template>
    <v-list 
        class="list"
        v-if="$store.state.goods.length > 0"
    >
        <v-list-item
            v-for="good of $store.state.goods"
            :key="good.id"
        >
            <div class="good_list_item">
                <div id="image">
                    <v-img 
                        src="https://www.ulfven.no/files/sculptor30/library/images/default-product-image.png"
                    />
                </div>
                <div id="description">
                    <h2
                        @click="this.$router.push(`/goods/${good.id}`);"
                        class="header_link"
                    >
                        {{good.name}}
                    </h2>
                    <p>{{good.short_description}}</p>
                </div>
                <div id="availability">
                    <p v-if="good.count > 10">В наличии</p>
                    <p v-else-if="good.count == 0">Нет в наличии</p>
                    <p v-else>Мало</p>
                </div>
                <div>
                   <p>{{good.cost}} руб.</p>
                    <v-btn
                        @click="addToBasket(good.id)"
                        class="add_to_basket_btn"
                        :disabled="good.count < 1"
                    >
                        В корзину
                    </v-btn> 
                </div>
                
            </div>
        </v-list-item>
    </v-list>
</template>

<script>
export default {
    name: "GoodList"
}
</script>

<style lang="scss" scoped>
.good_list_item {
    display: flex;
    align-items: center;
    justify-content: space-around;
    background-color: white;
    width: 80vw;
    height: 20vh;
    border-radius: 25px;
}
.add_to_basket_btn {
    background-color: rgb(107, 119, 228);
    color: white;
}
.list {
    background-color: $main_bcg_color;
}
#description {
    width: 15vw
}
#availability {
    width: 5vw;
}
#image {
    width: 10vw;
    height: 10vh;
}
</style>