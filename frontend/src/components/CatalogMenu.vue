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
            v-for="category in this.categories"
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
        categories: []
    }),
    methods: {
      fetchCategories() {
        this.categories = this.$store.state.categories;
      },
      changeCategory(id) {
        this.$router.push(`/catalog/${id}`);
      }
    },
    mounted() {
      this.fetchCategories();
    }
}
</script>