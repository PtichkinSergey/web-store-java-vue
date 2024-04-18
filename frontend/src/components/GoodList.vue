<template>
    <v-list 
        class="list"
        v-if="$store.state.goods.length > 0"
    >
        <v-list-item
            v-for="good of $store.state.goods"
            :key="good.id"
            class="good_list_item"
        >
            <div>
                <h2>{{good.name}}</h2>
                <p>{{good.short_description}}</p>
            </div>
            <p v-if="good.count > 10">В наличии</p>
            <p v-else-if="good.count == 0">Нет в наличии</p>
            <p v-else>Мало</p>
            <p>{{good.cost}}</p>
            <v-btn
                class="add_to_basket_btn"
                @click="addToBasket(good.id)"
                :disabled="good.count < 1"
            >
                В корзину
            </v-btn>
        </v-list-item>
    </v-list>
</template>

<script>
export default {
    name: "GoodList"
}
</script>

<style lang="scss">
.good_list_item {
    background-color: white;
    width: 80vw;
    height: 20vh;
    border-radius: 25px;
}
.add_to_basket_btn {
    background-color: rgb(107, 119, 228);
    color: white;
}
</style>