<template>
  <div class="text-center">
    <v-menu>
      <template v-slot:activator="{ props: menu }">
          <v-btn
            color="primary"
            dark
            v-bind="mergeProps(menu, tooltip)"
          >
            Каталог
          </v-btn>
      </template>
      <template
        v-for="category in $store.state.categories"
      >
        <SubCategoryMenuVue
          v-if="category.childs && category.childs.length != 0"
          :key="category.id"
          :menuList="category.childs"
        />
        <v-list-item
          v-else
          :key="category.id"
          @click="changeCategory(category.id)"
        >
          <v-list-item-title>{{ category.name }}</v-list-item-title>
        </v-list-item>
      </template>
    </v-menu>
  </div>
</template>

<script>
import { mergeProps } from 'vue'
import SubCategoryMenuVue from './SubCategoryMenu.vue';
export default {
    data: () => ({
        
    }),
    components:{
      SubCategoryMenuVue
    },
    methods: {
      mergeProps,
      async fetchCategories() {
        this.$store.dispatch('fetchCategories');
      },
      changeCategory(id) {
        this.$router.push({query: {category: id}});
      }
    },
    mounted() {
      this.fetchCategories();
    }
}
</script>