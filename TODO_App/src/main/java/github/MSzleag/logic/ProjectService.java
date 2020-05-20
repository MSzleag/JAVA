package github.MSzleag.logic;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import github.MSzleag.TaskConfigurationProperties;
import github.MSzleag.model.*;
import github.MSzleag.model.projection.GroupReadModel;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }
    public Project save(final Project toSave ){
        return repository.save(toSave);
    }
    public GroupReadModel createGroup(LocalDateTime deadline, int porjectId){
        if(!config.getTemplate().isallowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(porjectId))
        {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result = repository.findById(porjectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(step -> new Task(
                                            step.getDescription(),
                                            deadline.plusDays(step.getDaysToDeadline()))
                                    ).collect(Collectors.toSet())
                    );
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}
