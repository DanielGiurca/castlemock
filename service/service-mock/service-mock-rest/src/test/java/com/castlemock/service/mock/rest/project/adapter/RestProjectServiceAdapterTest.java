/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.service.mock.rest.project.adapter;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.service.mock.rest.RestTypeIdentifier;
import com.castlemock.service.mock.rest.project.*;
import com.castlemock.service.mock.rest.project.input.*;
import com.castlemock.service.mock.rest.project.output.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestProjectServiceAdapterTest {

    @Mock
    private CreateRestProjectService createRestProjectService;
    @Mock
    private DeleteRestProjectService deleteRestProjectService;
    @Mock
    private UpdateRestProjectService updateRestProjectService;
    @Mock
    private ReadAllRestProjectsService readAllRestProjectsService;
    @Mock
    private ReadRestProjectService readRestProjectService;
    @Mock
    private ExportRestProjectService exportRestProjectService;
    @Mock
    private ImportRestProjectService importRestProjectService;
    @Mock
    private SearchRestProjectService searchRestProjectService;

    @InjectMocks
    private RestProjectServiceAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreate(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final CreateRestProjectOutput output = CreateRestProjectOutput.builder().savedRestApplication(project).build();

        Mockito.when(createRestProjectService.process(Mockito.any(CreateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(createRestProjectService, Mockito.times(1)).process(Mockito.any(CreateRestProjectInput.class));
    }

    @Test
    public void testDelete(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final DeleteRestProjectOutput output = DeleteRestProjectOutput.builder().project(project).build();

        Mockito.when(deleteRestProjectService.process(Mockito.any(DeleteRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(deleteRestProjectService, Mockito.times(1)).process(Mockito.any(DeleteRestProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final UpdateRestProjectOutput output = UpdateRestProjectOutput.builder().updatedRestProject(project).build();

        Mockito.when(updateRestProjectService.process(Mockito.any(UpdateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(updateRestProjectService, Mockito.times(1)).process(Mockito.any(UpdateRestProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final List<RestProject> projects = Arrays.asList(project);
        final ReadAllRestProjectsOutput output = ReadAllRestProjectsOutput.builder().restProjects(projects).build();

        Mockito.when(readAllRestProjectsService.process(Mockito.any(ReadAllRestProjectsInput.class))).thenReturn(output);

        final List<RestProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(readAllRestProjectsService, Mockito.times(1)).process(Mockito.any(ReadAllRestProjectsInput.class));
    }

    @Test
    public void testRead(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final ReadRestProjectOutput output = ReadRestProjectOutput.builder().restProject(project).build();

        Mockito.when(readRestProjectService.process(Mockito.any(ReadRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.read(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(readRestProjectService, Mockito.times(1)).process(Mockito.any(ReadRestProjectInput.class));
    }

    @Test
    public void testGetTypeIdentifier(){
        final TypeIdentifier typeIdentifier = adapter.getTypeIdentifier();

        Assert.assertTrue(typeIdentifier instanceof RestTypeIdentifier);
    }

    @Test
    public void testExportProject(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final String exportedProject = "Exported project";
        final ExportRestProjectOutput output = ExportRestProjectOutput.builder().exportedProject(exportedProject).build();

        Mockito.when(exportRestProjectService.process(Mockito.any(ExportRestProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(exportRestProjectService, Mockito.times(1)).process(Mockito.any(ExportRestProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final String importedProject = "Imported project";
        final ImportRestProjectOutput output = ImportRestProjectOutput.builder().project(project).build();

        Mockito.when(importRestProjectService.process(Mockito.any(ImportRestProjectInput.class))).thenReturn(output);

        final Project returnedProject = adapter.importProject(importedProject);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(importRestProjectService, Mockito.times(1)).process(Mockito.any(ImportRestProjectInput.class));
    }
    
    @Test
    public void testSearch(){
        final List<SearchResult> searchResults = new ArrayList<>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setLink("Link");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");
        searchResults.add(searchResult);
        final SearchRestProjectOutput output = SearchRestProjectOutput.builder().searchResults(searchResults).build();

        Mockito.when(searchRestProjectService.process(Mockito.any(SearchRestProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
