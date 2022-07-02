package CacheInMemory;

import CacheInterface.Cache;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCacheMemory<K extends String, V extends String> implements Cache<K, V>
    {
        private Node<K, V> mostRecentlyUsed;
        private Node<K, V> leastRecentlyUsed;
        private int currentSize;
        private int maxSize;
        private Map<K, Node<K, V>> cacheElementsMap;
        private Node<K, V> dummyNode;

        public LRUCacheMemory(int maxSize)
            {
                this.leastRecentlyUsed = new Node<>(null, null, null, null);
                this.mostRecentlyUsed = leastRecentlyUsed;
                this.maxSize = maxSize;
                this.currentSize = 0;
                this.cacheElementsMap = new HashMap<>(maxSize);
            }

        @Override
        public boolean put(K key, V value)
            {
                Node<K, V> newNode;
                if (this.cacheElementsMap.containsKey(key)) {
                    Node<K, V> existingNode = cacheElementsMap.get(key);
                    this.updateAndMoveToFront(existingNode, value);
                    return true;
                } else {
                    newNode = new Node<>(mostRecentlyUsed, null, key, value);
                    this.makeNewMRU(newNode);
                }

                if (newNode.isEmpty()) return false;

                if (currentSize == maxSize) {
                    this.evictElement();
                }//поинтер lru кидаем на самый 1 элемент, до вызова get или до заполнения кэша
                else if (currentSize < maxSize) {
                    if (currentSize == 0) {
                        this.leastRecentlyUsed = newNode;
                    }
                    this.currentSize++;
                }
                return true;
            }

        public void makeNewMRU(Node<K, V> newNode)
            {
                mostRecentlyUsed.setNext(newNode);
                cacheElementsMap.put(newNode.getKey(), newNode);
                mostRecentlyUsed = newNode;
            }

        public void evictElement()
            {
                cacheElementsMap.remove(leastRecentlyUsed.getKey());
                leastRecentlyUsed = leastRecentlyUsed.getNext();
                leastRecentlyUsed.getPrevious().setPrevious(null);
            }

        public void updateAndMoveToFront(@NotNull Node<K, V> node, V value)
            {
                node.setValue(value);

                if (node.isEmpty())
                    return;

                Node<K, V> nextNode = node.getNext();
                Node<K, V> previousNode = node.getPrevious();

                //если узел является lru , то передаем поинтер lru следующему узлу
                if (node == leastRecentlyUsed) {
                    nextNode.getPrevious().setNext(null);
                    leastRecentlyUsed = nextNode;
                }//если узел не является ни lru ни mru , то просто связываем узлы на которые он ссылается.
                else {
                    nextNode.getPrevious().setPrevious(previousNode);
                    previousNode.getNext().setNext(nextNode);
                }
                //делаем наше значение mru
                mostRecentlyUsed.setNext(node);
                node.setNext(null);
                mostRecentlyUsed = node;

            }


        @Override
        public Optional<V> get(K key)
            {
                Node<K, V> tempNode = cacheElementsMap.get(key);

                if (tempNode == null || tempNode.isEmpty()) {
                    return Optional.empty();
                } else if (tempNode == mostRecentlyUsed) {
                    return Optional.of(tempNode.getValue());
                }

                this.moveToFront(tempNode);

                return Optional.of(tempNode.getValue());
            }

        public void moveToFront(@NotNull Node<K, V> node)
            {
                if (!node.isEmpty()) {
                    this.updateAndMoveToFront(node, node.getValue());
                }
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
