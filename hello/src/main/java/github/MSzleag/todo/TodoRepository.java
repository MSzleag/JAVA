package github.MSzleag.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TodoRepository extends JpaRepository<Todo,Integer> {
    //
}
/*Dodać metodę addTodo(Todo newTodo) do TodoRepository
Użyć session.persist, żeby zapisać nowy obiekt w bazie
Zwrócić "utrwalony" obiekt
Dodać doPost do TodoServlet
Wykorzystać readValue oraz getInputStream z mappera i żądania
Zwracać nowe Todo
*/