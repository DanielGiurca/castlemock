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

package com.castlemock.service.mock.soap.project.adapter;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.service.mock.soap.SoapTypeIdentifier;
import com.castlemock.service.mock.soap.project.*;
import com.castlemock.service.mock.soap.project.input.*;
import com.castlemock.service.mock.soap.project.output.*;
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
public class SoapProjectServiceAdapterTest {

    @Mock
    private CreateSoapProjectService createSoapProjectService;
    @Mock
    private UpdateSoapProjectService updateSoapProjectService;
    @Mock
    private DeleteSoapProjectService deleteSoapProjectService;
    @Mock
    private ReadAllSoapProjectsService readAllSoapProjectsService;
    @Mock
    private ReadSoapProjectService readSoapProjectService;
    @Mock
    private ExportSoapProjectService exportSoapProjectService;
    @Mock
    private ImportSoapProjectService importSoapProjectService;
    @Mock
    private SearchSoapProjectService searchSoapProjectService;

    @InjectMocks
    private SoapProjectServiceAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final CreateSoapProjectOutput output = CreateSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(createSoapProjectService.process(Mockito.any(CreateSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(createSoapProjectService, Mockito.times(1)).process(Mockito.any(CreateSoapProjectInput.class));
    }

    @Test
    public void testDelete(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final DeleteSoapProjectOutput output = DeleteSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(deleteSoapProjectService.process(Mockito.any(DeleteSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(deleteSoapProjectService, Mockito.times(1)).process(Mockito.any(DeleteSoapProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final UpdateSoapProjectOutput output = UpdateSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(updateSoapProjectService.process(Mockito.any(UpdateSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(updateSoapProjectService, Mockito.times(1)).process(Mockito.any(UpdateSoapProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final List<SoapProject> projects = Arrays.asList(project);
        final ReadAllSoapProjectsOutput output = ReadAllSoapProjectsOutput.builder()
                .projects(projects)
                .build();

        Mockito.when(readAllSoapProjectsService.process(Mockito.any(ReadAllSoapProjectsInput.class))).thenReturn(output);

        final List<SoapProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(readAllSoapProjectsService, Mockito.times(1)).process(Mockito.any(ReadAllSoapProjectsInput.class));
    }

    @Test
    public void testRead(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final ReadSoapProjectOutput output = ReadSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(readSoapProjectService.process(Mockito.any(ReadSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.read(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(readSoapProjectService, Mockito.times(1)).process(Mockito.any(ReadSoapProjectInput.class));
    }

    @Test
    public void testGetTypeIdentifier(){
        final TypeIdentifier typeIdentifier = adapter.getTypeIdentifier();

        Assert.assertTrue(typeIdentifier instanceof SoapTypeIdentifier);
    }

    @Test
    public void testExportProject(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final String exportedProject = "Exported project";
        final ExportSoapProjectOutput output = ExportSoapProjectOutput.builder()
                .project(exportedProject)
                .build();

        Mockito.when(exportSoapProjectService.process(Mockito.any(ExportSoapProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(exportSoapProjectService, Mockito.times(1)).process(Mockito.any(ExportSoapProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final String importedProject = "Imported project";
        final ImportSoapProjectOutput output = ImportSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(importSoapProjectService.process(Mockito.any(ImportSoapProjectInput.class))).thenReturn(output);

        final Project returnedProject = adapter.importProject(importedProject);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(importSoapProjectService, Mockito.times(1)).process(Mockito.any(ImportSoapProjectInput.class));
    }

    @Test
    public void testSearch(){
        final List<SearchResult> searchResults = new ArrayList<>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setLink("Link");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");
        searchResults.add(searchResult);
        final SearchSoapProjectOutput output = SearchSoapProjectOutput.builder()
                .searchResults(searchResults)
                .build();

        Mockito.when(searchSoapProjectService.process(Mockito.any(SearchSoapProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
