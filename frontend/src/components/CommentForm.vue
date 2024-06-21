<template>
    <div>
        <v-dialog max-width="500">
            <template v-slot:activator="{ props: activatorProps }">
                <v-btn
                v-bind="activatorProps"
                color="surface-variant"
                text="Добавить комментарий"
                variant="flat"
                ></v-btn>
            </template>

            <template v-slot:default="{ isActive }">
                <v-card title="Комментарий">
                <div> 
                <div id="comment_text_area">
                    <v-textarea
                        label="Добавить комментарий"
                        variant="outlined"
                    >
                    </v-textarea>
                </div>
                <div id="rating">
                    <h3>Оценка: </h3>

                </div>
                <div>
                    <v-img :src="preview_image" class="uploading_image" />
                    <input type="file" accept="image/jpeg" @change=uploadImage>
                </div>
                <v-btn 
                    @click="sendComment"
                >
                    Отправить
                </v-btn>
            </div>
                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn
                        text="Close Dialog"
                        @click="isActive.value = false"
                    ></v-btn>
                </v-card-actions>
                </v-card>
            </template>
        </v-dialog>
    </div>
</template>

<script>
    export default {
        name: "CommentForm",
        data: () => ({
            comment: {},
            preview_image: null
        }),
        methods: {
            sendComment() {
                this.$store.commit('addComment', this.comment)
                this.$store.dispatch('sendComment', this.comment)
            },
            uploadImage(e) {
                const image = e.target.files[0];
                const reader = new FileReader();
                reader.readAsDataURL(image);
                reader.onload = e =>{
                    this.preview_image = e.target.result;
                };
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>