package lk.sliit.constants;

public class CustomQueries {

    public static final String GET_JOURNEY_COUNT =
                    "select " +
                    "journey_name, " +
                    "count(id) " +
                    "from rides " +
                    "where customer_id = :customerId " +
                    "group by journey_name " +
                    "order by count(id) desc";

    public static final String GET_SPENT_AMOUNT =
                    "select " +
                    "customer_id, " +
                    "sum(amount) " +
                    "from rides " +
                    "where customer_id = :customerId " +
                    "group by customer_id";

    public static final String GET_PAYMENT_SUM =
                    "select " +
                    "token_id, " +
                    "sum(amount) as amount " +
                    "from payments " +
                    "where token_id = :id " +
                    "group by token_id ";

}
