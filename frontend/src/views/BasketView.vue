<template>
    <div class="page">
        <Toolbar/>
        <div v-if="$store.state.basket.length > 0">
            <h3 class="page_title">Корзина</h3>
        </div>
        <div v-else>
            <h2 class="empty_page_header">Корзина пустая!</h2>
        </div>
        <div 
            v-if="$store.state.basket.length > 0"
            id="basket"
        >
            <BasketList/>
            <div class="card" id="summary">
                <h3>Ваш заказ:</h3>
                <p>Всего товаров: {{ $store.getters.getBasketTotalSize }}</p>
                <p 
                    id="discount"
                    v-if="$store.state.auth_email"
                >
                    Скидка: {{ $store.getters.getTotalDiscount }} руб.
                </p>
                <p v-if="$store.state.auth_email">
                    Итого: <b>{{ $store.getters.getBasketTotalCost - $store.getters.getTotalDiscount }} руб.</b>
                </p>
                <p v-else>
                    Итого: <b>{{ $store.getters.getBasketTotalCost }} руб.</b>
                </p>
                <v-btn
                    id="execute_order_btn"
                >
                    Оформить заказ
                </v-btn>
            </div>
        </div>
    </div>
</template>

<script>
    import Toolbar from '@/components/Toolbar.vue'
    import BasketList from '@/components/BasketList.vue'
    export default {
        components: {
            Toolbar, BasketList
        },
        name: 'basket',
    }
</script>

<style lang="scss" scoped>
#basket_list {
    width: 70vw;
    background-color: $main_bcg_color;
}
#basket {
    display: flex;
    justify-content: space-between;
}
#summary {
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: space-between;
    width: 15vw;
    height: 10vw;
}
#execute_order_btn {
    background-color: #a0c715;
}
#discount {
    color: $discount;
}
</style>