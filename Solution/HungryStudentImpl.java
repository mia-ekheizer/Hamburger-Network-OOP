package OOP.Solution;

import OOP.Provided.*;
import OOP.Solution.RestaurantImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HungryStudentImpl implements HungryStudent
{
    public int id;
    public String name;
    public ArrayList<Restaurant> ratedRestaurants;
    public ArrayList<Restaurant> favoriteRestaurants;
    public HashSet<HungryStudent> friends;

    //C'TOR
    public HungryStudentImpl(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.ratedRestaurants = new ArrayList<Restaurant>();
        this.favoriteRestaurants = new ArrayList<Restaurant>();
        this.friends = new HashSet<HungryStudent>();
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

    // adds s to this student's friends collection.
    @Override
    public HungryStudent addFriend(HungryStudent s) throws HungryStudent.SameStudentException, HungryStudent.ConnectionAlreadyExistsException
    {
        if (this.equals(s))
        {
            throw new HungryStudent.SameStudentException();
        }
        else if (this.friends.contains(s))
        {
            throw new HungryStudent.ConnectionAlreadyExistsException();
        }
        else
        {
            this.friends.add(s);
        }
        return (HungryStudent)this;
    }

    // returns the collection of the student's friends.
    @Override
    public Set<HungryStudent> getFriends()
    {
        return this.friends;
    }

    //returns a collection of favorite restaurants by rating than by distance than by id
    @Override
    public Collection<Restaurant> favoritesByRating(int r)
    {
       return (this.favoriteRestaurants).stream().filter(rest -> rest.averageRating() >= r).sorted(new compareByRating()).collect(Collectors.toList());
    }
}
