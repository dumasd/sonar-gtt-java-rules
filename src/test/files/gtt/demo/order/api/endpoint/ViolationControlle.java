
package gtt.demo.order.api.endpoint;

import bikframework.web.domain.ApiResponse;
import gtt.videomart.drmlicense.api.model.dto.BatchDrmStreamPsshDataResponseDTO;
import gtt.videomart.drmlicense.api.model.dto.BatchDrmStreamQueryDTO;
import gtt.videomart.drmlicense.api.model.dto.DrmLicenseQueryRequestDTO;
import gtt.videomart.drmlicense.api.model.dto.DrmStreamLicenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/demo/order") // Noncompliant {{OpenAPI class name must hava @Tag annotation}}
public interface ViolationControlle { // Noncompliant {{OpenAPI class name must match regex: ^I.*Controller$}}

    /**
     * Query Stream License.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream License", description = "Query Stream License") // Compliant
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = DrmStreamLicenseResponseDTO.class))})
    @PostMapping(value = "/stream-license/query/v1", consumes = {"application/json"}, produces = {"application/json"})
    // Compliant
    BaseResponse<DrmStreamLicenseResponseDTO> // Noncompliant {{OpenAPI method's return type must be [ApiResponse, ApiPagingResponse, ApiSubQueryResponse, ApiSubQueryCountResponse, PageResult]}}
    queryStreamLicense(@RequestBody DrmLicenseQueryRequestDTO request, @RequestHeader("Authorization") String accessToken, HttpServletRequest servletRequest);

    /**
     * Query Stream Pssh.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream Pssh", description = "Query Stream Pssh,app query") // Compliant
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BatchDrmStreamPsshDataResponseDTO.class))})
    @PostMapping(value = "/stream-pssh/batch-query/v1", consumes = {"application/json"}, produces = {"application/json"})
    // Compliant
    ApiResponse<BatchDrmStreamPsshDataResponseDTO> batchQueryStreamPssh(@RequestBody BatchDrmStream request // Noncompliant {{OpenAPI method parameter must be [BO, DTO, Command, Query, ApiSubQueryRequest, ApiSubQueryCountRequest]}}
            , @RequestHeader("Authorization") String accessToken);


    @Operation(summary = "Query Stream Pssh", description = "Query Stream Pssh,app query") // Compliant
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BatchDrmStreamPsshDataResponseDTO.class))})
    @PostMapping(value = "/stream/batch-query/v1", consumes = {"application/json"}, produces = {"application/json"})
        // Compliant
    ApiSubQueryResponse  // Compliant
    batchQueryStream(@RequestBody ApiSubQueryRequest request // Compliant
    );


    @Operation(summary = "Query Stream Pssh", description = "Query Stream Pssh,app query") // Compliant
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BatchDrmStreamPsshDataResponseDTO.class))})
    @PostMapping(value = "/stream/batch-query/v1", consumes = {"application/json"}, produces = {"application/json"})
        // Compliant
    ApiSubQueryResponse  // Compliant
    batchQueryStreamA(@RequestBody List<ApiSubQueryDT> request // Noncompliant {{OpenAPI method parameter's generic type must be [BO, DTO, Command, Query, ApiSubQueryRequest, ApiSubQueryCountRequest]}}
    );

    ApiSubQueryResponse batchQueryStream2(@RequestBody ApiSubQueryRequest request); // Noncompliant {{OpenAPI method must hava @Operation annotation}} {{OpenAPI method must hava @xxxMapping annotation}}
}
