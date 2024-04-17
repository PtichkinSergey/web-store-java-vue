<template>
    <v-card
        color="grey-lighten-4"
        height="200px"
        rounded="0"
        flat
    >
        <v-toolbar density="compact">

        <CatalogMenu/>

        <v-spacer></v-spacer>

        <v-btn>
            Войти
            <v-icon>mdi-account</v-icon>
        </v-btn>

        <v-btn>
            Корзина
            <v-icon>mdi-cart</v-icon>
        </v-btn>
        </v-toolbar>
    </v-card>
</template>

<script>
import CatalogMenu from '@/components/CatalogMenu.vue';

export default {
  name: 'ToolbarView',
  components: {
    CatalogMenu
  }
}
</script>

<style lang="scss">

</style>