 - ru.alexz.tinkofftesttask.controller.ContactApplicationController.getLatestApplicationByContactId 
метод по идентификатору находит и возвращает в ответе самую новую заявку контакта.

 - так же добавил ендпойнты для создания и удаления заявок и контактов (для тестов)
 
 - rest-api-test-task.http - файл с примерами запросов к апи

 - ендпойнты предоставляют потребителю сервиса возможность выбора формата ответа – JSON / XML 
 (есть на это тесты  + rest-api-test-task.http примеры запросов с разными форматами)

 - сваггер доступен после запуска приложения - /swagger-ui.html
 
 - так же добавил flyway для загрузки демонстрационных данных, миграции лежат в \src\main\resources\db\migration
 
 - добавил кастомный обработчик ошибок - ru.alexz.tinkofftesttask.exceptionhandler.CommonExceptionHandler, 
 для неизвестных ошибок будет ответ следующего вида: {
                                                       "error": {
                                                         "message": "Internal server error",
                                                         "httpStatus": "INTERNAL_SERVER_ERROR"
                                                       }
                                                     }
                                                                                                           
  

