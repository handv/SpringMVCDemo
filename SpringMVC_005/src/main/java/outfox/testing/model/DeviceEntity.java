package han.testing.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by handv on 2016/5/15.
 */
@Entity
@Table(name = "device", schema = "han_device", catalog = "")
public class DeviceEntity {
    private int id;
    private String assetId;
    private String model;
    private String os;
    private String resolution;
    private String network;
    private String owner;
    private String desc;
    private String time;
    private int type; //android=0;ios=1;others=2
    private String share;
    private List<ProductEntity> productByType;
    private String macId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "assetId", nullable = false, length = 50)
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "os", nullable = true, length = 50)
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Basic
    @Column(name = "resolution", nullable = true, length = 50)
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Basic
    @Column(name = "network", nullable = true, length = 50)
    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    @Basic
    @Column(name = "owner", nullable = false, length = 25)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "desc", nullable = true, length = 50)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "time", nullable = true, length = 50)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceEntity that = (DeviceEntity) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (assetId != null ? !assetId.equals(that.assetId) : that.assetId != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (resolution != null ? !resolution.equals(that.resolution) : that.resolution != null) return false;
        if (network != null ? !network.equals(that.network) : that.network != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (assetId != null ? assetId.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (resolution != null ? resolution.hashCode() : 0);
        result = 31 * result + (network != null ? network.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + type;
        return result;
    }

    @Basic
    @Column(name = "share", nullable = true, length = 50)
    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    @OneToMany
    public List<ProductEntity> getProductByType() {
        return productByType;
    }

    public void setProductByType(List<ProductEntity> productByType) {
        this.productByType = productByType;
    }
}
