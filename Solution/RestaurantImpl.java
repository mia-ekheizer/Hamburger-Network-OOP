package OOP.Solution;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import OOP.Provided.*;

public class RestaurantImpl implements Restaurant {
    int id;
    String name;
    int distFromTech;
    Set<String> menu;
    Map<int, int> studentRateMap;
    int numOfRates;
    int sumOfRates;

    //C'TOR
    @Override
    public RestaurantImpl(int id, String name, int distFromTech, Set<String> menu)
    {
        this.id = id;
        this.name = name;
        this.distFromTech = distFromTech;
        this.studentRateMap = new Map<int, int>();
        this.menu = menu;
        this.sumOfRates = 0;
        this.numOfRates = 0;
    }

    //getter for restaurant id.
    public int getId()
    {
        return this.id;
    }
    //getter for distFromTech
    @Override
    public int distance()
    {
        return this.distFromTech;
    }

    //adding rating to the restaurant
    @Override
    public Restaurant rate(HungryStudent s, int r)
    {
        if (r > 5 || r < 0)
        {
            throw RateRangeExecption;
        }
        if (this.studentRateMap.containsKey(s.getId()))
        {
            this.sumOfRates -= this.studentRateMap.get(s.getId());
            this.studentRateMap.replace(s.getId(), r);
        }
        else
        {
            this.studentRateMap.put(s.getId(), r);
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
        if ((o.compareTo(this) == 0) && (this.compareTo((Restaurant)o) == 0))
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
    public int compareTo(Object o)
    {
        if (!(o instanceof Restaurant))
        {
            throw
        }
        int diff = this.id - (Restaurant)o.getId();
    }
}
