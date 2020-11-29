package com.shivenderkumar.kitchenbook.model;

import java.util.ArrayList;

public class Recipe {
    private String chef_emailid;                    //fetch name and other deatils if required
    private String chef_name;
    private String recipe_name;
    private String image_url;                       //change string to URI
    private String total_cook_time;                 //change string to time
    private String upload_recipe_time;              //change string to time
    private String about;                           //maybe Sentences or paragraph
    private String ingredients;                     //change to list of string or sentence;
    private String steps;                           //list of sentences or paragraph
    private String tags;                            //list of string
    private int likes;
    private ArrayList<Comment> commentArrayList;    // list of comment objects  -> future scope to use Dagger

    public Recipe() {
    }

    public Recipe(String chef_emailid,String chef_name, String recipe_name, String image_url, String total_cook_time, String upload_recipe_time, String about, String ingredients, String steps, String tags, int likes,ArrayList<Comment> commentArrayList) {
        this.chef_emailid = chef_emailid;
        this.chef_name = chef_name;
        this.recipe_name = recipe_name;
        this.image_url = image_url;
        this.total_cook_time = total_cook_time;
        this.upload_recipe_time = upload_recipe_time;
        this.about = about;
        this.ingredients = ingredients;
        this.steps = steps;
        this.tags = tags;
        this.likes = likes;
        this.commentArrayList = commentArrayList;
    }

    public String getChef_emailid() {
        return chef_emailid;
    }

    public void setChef_emailid(String chef_emailid) {
        this.chef_emailid = chef_emailid;
    }

    public String getChef_name() {
        return chef_name;
    }

    public void setChef_name(String chef_name) {
        this.chef_name = chef_name;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTotal_cook_time() {
        return total_cook_time;
    }

    public void setTotal_cook_time(String total_cook_time) {
        this.total_cook_time = total_cook_time;
    }

    public String getUpload_recipe_time() {
        return upload_recipe_time;
    }

    public void setUpload_recipe_time(String upload_recipe_time) {
        this.upload_recipe_time = upload_recipe_time;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

}
