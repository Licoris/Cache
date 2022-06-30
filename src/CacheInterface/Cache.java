package CacheInterface;

import java.util.Map;
import java.util.Optional;

public interface Cache<K, V>
    {
        void put(K key, V value);

        Optional<V> get(K key);

        void clearCache();

        class Node<K, V>
            {
                public Node<K, V> previous;
                public Node<K, V> next;
                public K key;
                public V value;


                public Node(Node<K, V> previous, Node<K, V> next, K key, V value)
                    {
                        this.previous = previous;
                        this.next = next;
                        this.key = key;
                        this.value = value;
                    }

                @Override
                public String toString()
                    {
                        return "Node{" +
                                "key=" + key +
                                ", value=" + value +
                                '}';
                    }
            }

    }
