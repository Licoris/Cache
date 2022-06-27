package CacheInterface;

public interface Cache<K, V>
    {
        void put(K key, V value);

        V get(K key);

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
                        return "CacheElement{" +
                                "key=" + key +
                                ", value=" + value +
                                '}';
                    }
            }

    }
