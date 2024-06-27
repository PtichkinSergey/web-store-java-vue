<template>
    <v-menu :location="location">
      <template v-slot:activator="{ props: menu }">
          <v-btn
            v-bind="mergeProps(menu)"
            icon="mdi-chevron-right"
            class="sub_category_btn"
          >
          </v-btn>
      </template>
      <v-list>
          <v-list-item
            v-for="(category, index) in menuList"
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
                :key="category.id"
                :menuList="category.childs"
              />
            </v-list-item-title>
          </v-list-item>
      </v-list>
    </v-menu>
</template>

<script>
import { mergeProps } from 'vue'
export default {
    data: () => ({
        location: 'end'
    }),
    name: 'SubCategoryMenu',
    props: {
      menuList: {
          type: Array,
          default: () => {},
      },
    },
    methods: {
      mergeProps,
      changeCategory(id) {
        this.$router.push({query: {category: id}});
      }
    },
}
</script>