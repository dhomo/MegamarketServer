# MegamarketServer

Весь код написан за выходные потому много где написано весьма коряво.
За основу был взят код полученый кодогенерацией Swagger Codegen.
Перенес код с мавена на Gradle, мне с ним привычнее и не пришлось тратить время на разбирательства с мавеном.
По коду местами остатки спрингфокса, не успел его полностью выпилить. UI открывается, но с ошибкой(не может распарсить дату из примеров). 
При налчии внешней заданой спецификации на api генерировать её из кода избыточно, а код сильно зарастает лишними анатациями.
Весьма коряво реальзована валидация данных и обработка ошибок, требует рефакторинга.
CI CD заключался в том что вручную закинул jar и кофиг докера на сервер и ручками запустил. делалось это за 20 минут до дедлайна и на что то большее времени не оставалось.
На большой нагрузке (а можети на средней) вероятно будет тупить, обращений к бд будет много бестолковых.


TODO (несортированый)
~~1. Централизовано генерировать ответ с ошибкой. Для начала https://www.baeldung.com/global-error-handler-in-a-spring-rest-api~~
~~2. Валидацию данных ~~
3. ShopUnitService отрефакторить
4. Реализовать дополнительные задачи из спецификации
5. Настроить CI CD
6. Модульные тесты и интеграционные тесты вписаные в CI CD
7. Полноценно разобратсья с древовидной стрктурой хранения и её рализацией в spring jpa

8. Переписать на Quarkus реактивный под graalvm
 
