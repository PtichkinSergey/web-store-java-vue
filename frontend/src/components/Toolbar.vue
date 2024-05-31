<template>
    <v-card>
        <v-toolbar density="compact">
        <CategoryMenu v-if="this.$route.name == `catalog`" />
        <v-btn 
          v-else
          @click="this.$router.push('/catalog')"
          color="primary"
          dark
        >
          Каталог
        </v-btn>
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
import CategoryMenu from '@/components/CategoryMenu.vue';

export default {
  name: 'ToolbarView',
  components: {
    CategoryMenu
  }
}
</script>

<style lang="scss">

</style>