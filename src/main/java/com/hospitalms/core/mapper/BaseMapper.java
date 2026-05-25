package com.hospitalms.core.mapper;

import java.util.List;

public interface BaseMapper<Model, Response> {

    Response toResponse(Model model);

    List<Response> toResponseList(List<Model> models);
}