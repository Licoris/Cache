package CacheInterface;

import java.io.Serializable;
import java.util.Optional;

public interface Cache<K, V>
    {
        boolean put(K key, V value);

        Optional<V> get(K key);

        void clearCache();

        class Node<K, V> implements Serializable
            {
                private Node<K, V> previous;
                private Node<K, V> next;
                private K key;
                private V value;

                public K getKey()
                    {
                        return key;
                    }

                public void setKey(K key)
                    {
                        this.key = key;
                    }

                public V getValue()
                    {
                        return value;
                    }

                public void setValue(V value)
                    {
                        this.value = value;
                    }

                public Node<K, V> getPrevious()
                    {
                        return previous;
                    }

                public void setPrevious(Node<K, V> previous)
                    {
                        this.previous = previous;
                    }

                public Node<K, V> getNext()
                    {
                        return next;
                    }

                public void setNext(Node<K, V> next)
                    {
                        this.next = next;
                    }

                public boolean isEmpty()
                    {
                        return this.value == null;
                    }

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
