package category.controller;



import ad.service.AdService;
import category.dto.CreateCategoryRequest;
import category.dto.GetCategoriesResponse;
import category.dto.GetCategoryResponse;
import category.dto.UpdateCategoryRequest;
import category.entity.Category;
import category.service.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

/**
 * REST controller for {@link category.entity.Category} entity.
 */
@Path("")
public class CategoryController {

    private CategoryService categoryService;
    private AdService adService;

    public CategoryController() {
    }

    @Inject
    public void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}

    @Inject
    public void setAdService(AdService adService){this.adService = adService;}

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        return Response.ok(GetCategoriesResponse.entityToDtoMapper().apply(categoryService.findAll())).build();
    }

    @GET
    @Path("/categories/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("name") String categoryName) {
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            return Response.ok(GetCategoryResponse.entityToDtoMapper().apply(category.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/categories")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCategory(CreateCategoryRequest request) {
        Category category = CreateCategoryRequest.dtoToEntityMapper().apply(request);
        categoryService.create(category);
        return Response.created(UriBuilder.fromMethod(CategoryController.class, "getCategory").build(category.getName())).build();
    }

    @PUT
    @Path("/categories/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCategory(@PathParam("name") String categoryName , UpdateCategoryRequest request) {
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            UpdateCategoryRequest.dtoToEntityUpdater().apply(category.get(), request);

            categoryService.update(category.get());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/categories/{name}")
    public Response deleteCategory(@PathParam("name") String categoryName) {
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            categoryService.delete(categoryName);
        }
        return Response.status(Response.Status.OK).build();
    }
}
