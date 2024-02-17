package OOP.Solution;

import OOP.Provided.HamburgerNetwork;
import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import java.util.*;

public class HamburgerNetworkImpl implements HamburgerNetwork {
    public ArrayList<HungryStudent> registeredStudents;
    public ArrayList<Restaurant> registeredRestaurants;
    public HamburgerNetworkImpl()
    {
        this.registeredStudents = new ArrayList<HungryStudent>();
        this.registeredRestaurants = new ArrayList<Restaurant>();
    }

    @Override
    public HungryStudent joinNetwork(int id, String name) throws HungryStudent.StudentAlreadyInSystemException
    {
        HungryStudent newStudent = new HungryStudentImpl(id, name);
        if (this.registeredStudents.contains(newStudent))
        {
            throw new HungryStudent.StudentAlreadyInSystemException();
        }
        this.registeredStudents.add(newStudent);
        return newStudent;
    }

    @Override
    public Restaurant addRestaurant(int id, String name, int dist, Set<String> menu) throws Restaurant.RestaurantAlreadyInSystemException
    {
        Restaurant newRestaurant = new RestaurantImpl(id,name, dist, menu);
        if (this.registeredRestaurants.contains(newRestaurant))
        {
            throw new Restaurant.RestaurantAlreadyInSystemException();
        }

        this.registeredRestaurants.add(newRestaurant);
        return newRestaurant;
    }

    @Override
    public Collection<HungryStudent> registeredStudents()
    {
        return registeredStudents;
    }

    @Override
    public Collection<Restaurant> registeredRestaurants()
    {
        return  registeredRestaurants;
    }

    @Override
    public HungryStudent getStudent(int id) throws HungryStudent.StudentNotInSystemException
    {
        HungryStudent wantedStudent = new HungryStudentImpl(id, "");
        if (!this.registeredStudents.contains(wantedStudent))
        {
            throw new HungryStudent.StudentNotInSystemException();
        }
        for (HungryStudent s : this.registeredStudents)
        {
            if (s.equals(wantedStudent))
            {
                wantedStudent = s;
            }
        }
        return wantedStudent;
    }

    @Override
    public Restaurant getRestaurant(int id) throws Restaurant.RestaurantNotInSystemException
    {
        Restaurant wantedRestaurant = new RestaurantImpl(id,"", 0, null);
        if (! this.registeredRestaurants.contains(wantedRestaurant))
        {
            throw new Restaurant.RestaurantNotInSystemException();
        }
        for (Restaurant r : this.registeredRestaurants)
        {
            if (r.equals(wantedRestaurant))
            {
                wantedRestaurant = r;
            }
        }
        return wantedRestaurant;
    }

    @Override
    public HamburgerNetwork addConnection(HungryStudent s1, HungryStudent s2) throws HungryStudent.StudentNotInSystemException, HungryStudent.ConnectionAlreadyExistsException, HungryStudent.SameStudentException
    {
        if (!this.registeredStudents.contains(s1) || !this.registeredStudents.contains(s2))
        {
            throw new HungryStudent.StudentNotInSystemException();
        }
        if (s1.equals(s2))
        {
            throw new HungryStudent.SameStudentException();
        }
        if (s1.getFriends().contains(s2) || s2.getFriends().contains(s1))
        {
            throw new HungryStudent.ConnectionAlreadyExistsException();
        }
        s1.addFriend(s2);
        s2.addFriend(s1);
        return (HamburgerNetwork)this;
    }

    @Override
    public Collection<Restaurant> favoritesByRating(HungryStudent s) throws HungryStudent.StudentNotInSystemException
    {
        if (!this.registeredStudents.contains(s))
        {
            throw new HungryStudent.StudentNotInSystemException();
        }
        ArrayList<Restaurant> ret = new ArrayList<Restaurant>();
        List<HungryStudent> sortedFriends = s.getFriends().stream().sorted().toList();
        for (HungryStudent friend : sortedFriends)
        {
            Collection<Restaurant> sortedFavorites = friend.favoritesByRating(0);
            for (Restaurant r : sortedFavorites)
            {
                if (!ret.contains(r))
                {
                    ret.add(r);
                }
            }
        }
        return ret;
    }

    @Override
    public Collection<Restaurant> favoritesByDist(HungryStudent s) throws HungryStudent.StudentNotInSystemException
    {
        if (!this.registeredStudents.contains(s))
        {
            throw new HungryStudent.StudentNotInSystemException();
        }
        ArrayList<Restaurant> ret = new ArrayList<Restaurant>();
        List<HungryStudent> sortedFriends = s.getFriends().stream().sorted().toList();
        for (HungryStudent friend : sortedFriends)
        {
            Collection<Restaurant> sortedFavorites = ((HungryStudentImpl)friend).favoritesByDist();
            for (Restaurant r : sortedFavorites)
            {
                if (!ret.contains(r))
                {
                    ret.add(r);
                }
            }
        }
        return ret;
    }

    @Override
    public String toString()
    {
        //adding students to string
        List<HungryStudent> sortedStudents = this.registeredStudents.stream().sorted().toList();
        String ret = "Registered students: ";
        if (!this.registeredStudents.isEmpty())
        {
            for (HungryStudent s : sortedStudents)
            {
                ret += ((HungryStudentImpl)s).id + ", ";
            }
            ret = ret.substring(0, ret.length() - 2);
        }
        ret += ".\n";

        //adding restaruants to string
        List<Restaurant> sortedRestaurants = this.registeredRestaurants.stream().sorted().toList();
        ret += "Registered restaurants: ";
        if (!this.registeredRestaurants.isEmpty())
        {
            for (Restaurant r : sortedRestaurants)
            {
                ret += ((RestaurantImpl)r).id + ", ";
            }
            ret = ret.substring(0, ret.length() - 2);
        }
        ret += ".\n";

        //adding students with details to string
        ret += "Students:\n";
        if (!this.registeredStudents.isEmpty())
        {
            for (HungryStudent s : sortedStudents)
            {
                ret += ((HungryStudentImpl)s).id + " -> " + s.getFriends() + ".\n";
            }
        }
        ret += "End students.";
        return ret;
    }

    @Override
    public boolean getRecommendation(HungryStudent s, Restaurant r, int t) throws HungryStudent.StudentNotInSystemException, Restaurant.RestaurantNotInSystemException, HamburgerNetwork.ImpossibleConnectionException
    {
        if (!this.registeredStudents.contains(s))
        {
            throw new HungryStudent.StudentNotInSystemException();
        }
        if (!this.registeredRestaurants.contains(r))
        {
            throw new Restaurant.RestaurantNotInSystemException();
        }
        if  (t < 0)
        {
            throw new HamburgerNetwork.ImpossibleConnectionException();
        }
        Map<HungryStudent, Integer> visitedList = new HashMap<HungryStudent, Integer>();
        List<HungryStudent> toIterate = new ArrayList<HungryStudent>();

        toIterate.add(s);
        visitedList.put(s, 0);

        getRecommendationAux(t, visitedList, toIterate);

        for (HungryStudent student : visitedList.keySet())
        {
            if (student.favorites().contains(r))
            {
                return true;
            }
        }
        return false;
    }

    public void getRecommendationAux(int t, Map<HungryStudent, Integer> visitedList, List<HungryStudent> toIterate)
    {
        while (!toIterate.isEmpty())
        {
            HungryStudent next = toIterate.getFirst();
            for (HungryStudent friend : next.getFriends())
            {
                if (!visitedList.containsKey(friend) && visitedList.get(next) < t)
                {
                    toIterate.add(friend);
                    visitedList.put(friend, visitedList.get(next) + 1);
                }
            }
            toIterate.removeFirst();
        }
    }
}
