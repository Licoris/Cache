package Caches;

public class TestClass
    {
        public static void main(String[] args)
            {
                LRUCache<Integer, Integer> lru = new LRUCache<>(3);
                lru.put(1, 1);
                lru.put(2, 2);
                lru.put(3, 3);
                System.out.println(lru);
                lru.get(3);
                lru.get(2);
                lru.put(4,4);
                System.out.println(lru.leastRecentlyUsed);
                System.out.println(lru.mostRecentlyUsed);
                lru.get(3);
                lru.get(2);
                System.out.println(lru.leastRecentlyUsed);
                System.out.println(lru.mostRecentlyUsed);
                lru.put(5,5);
                System.out.println(lru);


            }
    }
