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
          <v-list-item
            v-for="(category, index) in $store.getters.getCategoriesJSON"
            :key="index"
            @click="changeCategory(category.id)"
            id="sub_category_menu"
          >
            <v-list-item-title 
              class="sub_category_title"
            >
              {{ category.name }}
              <SubCategoryMenu
                v-if="category.childs && category.childs.length != 0"
                :menuList="category.childs"
              />  
            </v-list-item-title>
          </v-list-item>
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

<style>

</style>