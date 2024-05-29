<template>
    <div>
        <v-checkbox
            v-model="availability"
            label="В наличии"
        ></v-checkbox>
        <p>Цена:</p>
        
    </div>
</template>
<script>
export default {
    name: "FilterPanel",
    data: () => ({
        availability: false
    }),
    methods: {
        
    },
    mounted() {
        
    },
}
</script>

<style>

</style>