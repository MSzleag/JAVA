package github.MSzleag.logic;

import github.MSzleag.TaskConfigurationProperties;
import github.MSzleag.model.ProjectRepository;
import github.MSzleag.model.TaskGroupRepository;
import github.MSzleag.model.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(final ProjectRepository repository,
                                  final TaskGroupRepository taskGroupRepository,
                                  final TaskConfigurationProperties config
    ){
        return new ProjectService(repository,taskGroupRepository,config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository repository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(repository, taskRepository);
    }
}
