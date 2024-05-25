<template>
  <div class="text-center">
    <v-menu>
      <template v-slot:activator="{ props: menu }">
          <v-btn
            color="primary"
            dark
            v-bind="mergeProps(menu)"
          >
            Каталог
          </v-btn>
      </template>
      <v-list>
        <template
          v-for="(category, index) in $store.getters.getCategoriesJSON"
        >
          <SubCategoryMenu
            v-if="category.childs && category.childs.length != 0"
            :key="category.id"
            :menuList="category"
          />
          <v-list-item
            v-else
            :key="index"
            @click="changeCategory(category.id)"
          >
            <v-list-item-title>{{ category.name }}</v-list-item-title>
          </v-list-item>
        </template>
      </v-list>
      
    </v-menu>
  </div>
</template>

<script>
import { mergeProps } from 'vue'
import SubCategoryMenu from './SubCategoryMenu.vue';
export default {
    data: () => ({
        
    }),
    components:{
      SubCategoryMenu
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