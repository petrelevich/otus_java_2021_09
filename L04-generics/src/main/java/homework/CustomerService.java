package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> customersData = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return makeEntryWithProxyCustomer(customersData.firstEntry()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return makeEntryWithProxyCustomer(customersData.higherEntry(customer)); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customersData.put(customer, data);
    }

    private Map.Entry<Customer, String> makeEntryWithProxyCustomer(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        var proxyCustomer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return new AbstractMap.SimpleImmutableEntry<>(proxyCustomer, entry.getValue());
    }
}
