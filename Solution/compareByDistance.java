package OOP.Solution;

import OOP.Provided.Restaurant;

import java.util.Comparator;

public class compareByDistance implements Comparator<Restaurant>
{
    @Override
    public int compare(Restaurant first, Restaurant second)
    {
        if (first.distance() > second.distance())
        {
            return 1;
        }
        else if (first.distance() < second.distance())
        {
            return -1;
        }
        else {
            if (first.averageRating() < second.averageRating())
            {
                return 1;
            }
            else if (first.averageRating() > second.averageRating())
            {
                return -1;
            }
            else
            {
                return first.compareTo(second);
            }
        }
    }
}
