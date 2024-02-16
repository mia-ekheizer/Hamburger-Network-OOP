package OOP.Solution;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import OOP.Provided.*;
import OOP.Solution.HungryStudentImpl;

public class RestaurantImpl implements Restaurant {
    public int id;
    public String name;
    public int distFromTech;
    public Set<String> menu;
    public HashMap<Integer, Integer> studentRateMap;
    public int numOfRates;
    public int sumOfRates;

    //C'TOR
    public RestaurantImpl(int id, String name, int distFromTech, Set<String> menu)
    {
        this.id = id;
        this.name = name;
        this.distFromTech = distFromTech;
        this.studentRateMap = new HashMap<Integer, Integer>();
        this.menu = menu;
        this.sumOfRates = 0;
        this.numOfRates = 0;
    }

    //getter for distFromTech
    @Override
    public int distance()
    {
        return this.distFromTech;
    }

    //adding rating to the restaurant
    @Override
    public Restaurant rate(HungryStudent s, int r) throws Restaurant.RateRangeException
    {
        if (r > 5 || r < 0)
        {
            throw new Restaurant.RateRangeException();
        }
        if (this.studentRateMap.containsKey(((HungryStudentImpl)s).id))
        {
            this.sumOfRates -= this.studentRateMap.get(((HungryStudentImpl)s).id);
            this.studentRateMap.replace(((HungryStudentImpl)s).id, r);
        }
        else
        {
            this.studentRateMap.put(((HungryStudentImpl)s).id, r);
            this.numOfRates++;
        }
        this.sumOfRates += r;
        return this;
    }

    //getter for numOfRates
    @Override
    public int numberOfRates()
    {
        return this.numOfRates;
    }

    //returns average of ratings
    @Override
    public double averageRating()
    {
        if (this.numOfRates == 0)
        {
            return 0;
        }
        return (((double) this.sumOfRates) / this.numOfRates);
    }

    //overriding the equals method
    //restaurants are equal only if their id is the same
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Restaurant))
        {
            return false;
        }
        if ((((Restaurant)o).compareTo(this) == 0) && (this.compareTo((Restaurant)o) == 0))
        {
            return true;
        }
        return false;
    }

    //returns the restaurant description
    @Override
    public String toString()
    {
        String ret =
         "Restaurant: " + this.name + ".\n" +
         "Id: " + this.id + ".\n" +
        "Distance: " + this.distFromTech + ".\n" +
        "Menu: " + this.menu.stream().sorted();
        return ret;
    }

    //overriding the compareTo method
    @Override
    public int compareTo(Restaurant r)
    {
        return this.id - ((RestaurantImpl)r).id;
    }
}

public class compareByRating implements Comparator<Restaurant>
{
    @Override
    public int compare(Restaurant first, Restaurant second)
    {
        if (first.averageRating() < second.averageRating())
        {
            return 1;
        } else if (first.averageRating() > second.averageRating()) {
            return -1;
        }
        else
        {
            if (first.distance() > second.distance())
            {
                return 1;
            } else if (first.distance() < second.distance()) {
                return -1;
            }
            else {
                return first.compareTo(second);
            }
        }
    }
}

public class compareByDistance implements Comparator<Restaurant>
{
    @Override
    public int compare(Restaurant first, Restaurant second)
    {
        if (first.distance() > second.distance()
        {
            return 1;
        } else if (first.distance() < second.distance()) {
            return -1;
        }
        else
        {
            if (first.averageRating() < second.averageRating())
            {
                return 1;
            } else if (first.averageRating() > second.averageRating()) {
                return -1;
            }
            else {
                return first.compareTo(second);
            }
        }
    }
}

