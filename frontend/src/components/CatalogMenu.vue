<template>
    <div class="text-center">
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn
            color="primary"
            dark
            v-bind="props"
          >
            Каталог
          </v-btn>
        </template>
  
        <v-list>
          <v-list-item
            v-for="category in $store.state.categories"
            :key="category.id"
            @click="changeCategory(category.id)"
          >
            <v-list-item-title>{{ category.name }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
</template>

<script>
export default {
    data: () => ({
        
    }),
    methods: {
      fetchCategories() {
        this.$store.dispatch('fetchCategories');
      },
      changeCategory(id) {
        this.$router.push({query: {category: id}});
        this.$router.go();
      }
    },
    mounted() {
      this.fetchCategories();
    }
}
</script>