<template>
    <v-card>
        <v-toolbar density="compact">
        <CatalogMenu/>
        <v-spacer></v-spacer>
        <v-btn @click="this.$router.push('/auth');"
        >
            Войти
            <v-icon>mdi-account</v-icon>
        </v-btn>
        <v-btn @click="this.$router.push('/basket');"
        >
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