package CacheInMemory;

import CacheInterface.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCacheMemory<K extends String, V extends String> implements Cache<K, V>
    {
        private int currentSize;
        private int maxSize;
        private Node<K, V> mostRecentlyUsed;

        private Node<K, V> leastRecentlyUsed;
        private Map<K, Node<K, V>> cacheElementsMap;


        public LRUCacheMemory(int maxSize)
            {
                this.leastRecentlyUsed = new Node<>(null, null, null, null);
                this.mostRecentlyUsed = leastRecentlyUsed;
                this.maxSize = maxSize;
                this.currentSize = 0;
                this.cacheElementsMap = new HashMap<>(maxSize);
            }

        @Override
        public void put(K key, V value)
            {
                //Если ключ уже содержится, то выходим из метода
                if (cacheElementsMap.containsKey(key)) {
                    System.out.println("Key already exists.");
                    return;
                }

                //Создаем новый узел по заданному ключу и значению
                Node<K, V> newNode = new Node<>(mostRecentlyUsed, null, key, value);

                //меняем ссылку старого mru на уже новый mru
                mostRecentlyUsed.next = newNode;
                //Добавляем узел в мапу
                cacheElementsMap.put(key, newNode);
                //указываем значение нового mru
                mostRecentlyUsed = newNode;

                //проверяем достигнут ли максимальный размер,если да - удаляем lru
                if (currentSize == maxSize) {
                    cacheElementsMap.remove(leastRecentlyUsed.key);
                    leastRecentlyUsed = leastRecentlyUsed.next;
                    leastRecentlyUsed.previous = null;
                }//поинтер lru кидаем на самый 1 элемент, до 1 get или до заполнения кэша
                else if (currentSize < maxSize) {
                    if (currentSize == 0) {
                        leastRecentlyUsed = newNode;
                    }

                    currentSize++;
                }
            }


        @Override
        public Optional<V> get(K key)
            {
                //получаем значение ключа из хэшмап
                Node<K, V> tempNode = cacheElementsMap.get(key);

                //проверяем существует ли ключ
                if (tempNode == null) {
                    return Optional.empty();
                }
                //если значение mru , просто возвращаем его
                else if (tempNode == mostRecentlyUsed) {
                    return Optional.ofNullable(tempNode.value);
                }

                //создаем значения для связки 2 узлов после и перед нашим основным узлом.
                Node<K, V> nextNode = tempNode.next;
                Node<K, V> previousNode = tempNode.previous;

                //если узел является lru , то передаем поинтер lru следующему узлу
                if (tempNode == leastRecentlyUsed) {
                    nextNode.previous = null;
                    leastRecentlyUsed = nextNode;
                }//если узел не является ни lru ни mru , то просто связываем узлы на которые он ссылается.
                else {
                    nextNode.previous = previousNode;
                    previousNode.next = nextNode;
                }

                //делаем наше значение mru
                mostRecentlyUsed.next = tempNode;
                tempNode.next = null;
                mostRecentlyUsed = tempNode;

                return Optional.ofNullable(tempNode.value);
            }


        //очищаем хэшмап, обнуляем размер кэша, также обновляем значения lru и mru на начальные
        public void clearCache()
            {
                cacheElementsMap.clear();

                leastRecentlyUsed = new Node<>(null, null, null, null);
                mostRecentlyUsed = leastRecentlyUsed;

                currentSize = 0;
            }

        @Override
        public String toString()
            {
                return "LRUCache: " + cacheElementsMap.values();
            }
    }
