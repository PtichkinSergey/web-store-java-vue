<template>
    <v-menu :location="location">
      <template v-slot:activator="{ props: menu }">
          <v-list-item
            v-bind="mergeProps(menu)"
          >
            {{ menuList.name }}
          </v-list-item>
      </template>
      <v-list>
        <template
          v-for="(category, index) in menuList.childs"
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
          type: Object,
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