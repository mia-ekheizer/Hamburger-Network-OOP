package OOP.Solution;

import OOP.Provided.*;
import OOP.Solution.RestaurantImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class HungryStudentImpl {
    int id;
    String name;
    ArrayList<Restaurant> ratedRestaurants;
    ArrayList<Restaurant> favoriteRestaurants;
    HashSet<HungryStudentImpl> friends;

    //C'TOR
    public HungryStudentImpl(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.ratedRestaurants = new ArrayList<Restaurant>();
        this.favoriteRestaurants = new ArrayList<Restaurant>();
        this.friends = new HashSet<HungryStudentImpl>();
    }

    //adding a restaurant as a favorite
    @Override
    public HungryStudent favorite(Restaurant r) throws HungryStudent.UnratedFavoriteRestaurantException {
        if (!this.ratedRestaurants.contains(r))
        {
            throw new HungryStudent.UnratedFavoriteRestaurantException();
        }
        this.favoriteRestaurants.add(r);
        return (HungryStudent)this;
    }

    //returning a collection of the favorite restaurants
    @Override
    public Collection<Restaurant> favorites()
    {
        return this.favoriteRestaurants;
    }


}
