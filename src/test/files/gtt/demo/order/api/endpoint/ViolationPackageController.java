package gtt.demo.order.apiaaa.endpoint; // Noncompliant {{The package of the class does not comply with either the v1 or v2 conventions.}}

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

/**
 * @author kevin
 */
@Tag(name = "App License Manage", description = "license api for front-end")
@RequestMapping("/api/demo/order")
public interface IOrderController {

    /**
     * Query Stream License.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream License", description = "Query Stream License")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = DrmStreamLicenseResponseDTO.class))})
    @PostMapping(value = "/stream-license/query/v1", consumes = {"application/json"}, produces = {"application/json"})
    ApiResponse<DrmStreamLicenseResponseDTO> queryStreamLicense(@RequestBody DrmLicenseQueryRequestDTO request, @RequestHeader("Authorization") String accessToken, HttpServletRequest servletRequest);

    /**
     * Query Stream Pssh.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream Pssh", description = "Query Stream Pssh,app query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BatchDrmStreamPsshDataResponseDTO.class))})
    @PostMapping(value = "/stream-pssh/batch-query/v1", consumes = {"application/json"}, produces = {"application/json"})
    ApiResponse<BatchDrmStreamPsshDataResponseDTO> batchQueryStreamPssh(@RequestBody BatchDrmStreamQueryDTO request, @RequestHeader("Authorization") String accessToken);

}
