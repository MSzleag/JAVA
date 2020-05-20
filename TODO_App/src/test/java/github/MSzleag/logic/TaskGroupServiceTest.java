package github.MSzleag.logic;

import github.MSzleag.model.TaskGroup;
import github.MSzleag.model.TaskGroupRepository;
import github.MSzleag.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when undone task exist")
    void toggleGroup_undoneTasksExist_throwsIllegalStateException() {
        //given
        TaskRepository mockRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService(null,mockRepository);

        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when no group for a given id")
    void toggleGroup_noGroup_throwsIllegalArgumentException() {
        //given
        TaskRepository mockRepository = taskRepositoryReturning(false);
        //and
        TaskGroupRepository mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockGroupRepository,mockRepository);

        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    @Test
    @DisplayName("Should toggle group")
    void toggleGroup_worksAsExpected() {
        //given
        TaskRepository mockRepository = taskRepositoryReturning(false);
        //and
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        TaskGroupRepository mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new TaskGroupService(mockGroupRepository,mockRepository);

        //when
        toTest.toggleGroup(1);

        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean b) {
        TaskRepository mockRepository = mock(TaskRepository.class);
        when(mockRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(b);
        return mockRepository;
    }
}