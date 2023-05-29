package com.neutrinosvs.employees;

public interface Chef {
    String favouriteFood = "hamburger";

    default void cook(String food) {
        System.out.println("I'm now cooking " + food);
    }

    default String cleanUp() {
        return "I'm done cleaning up";
    }

    default String getFavouriteFood() {
        return favouriteFood;
    }

}
