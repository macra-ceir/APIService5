package com.gl.mdr.model.app;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Table;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class UserProfile {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String companyName;
    private Integer type;
    private Integer vatStatus;
    private String vatNo;
    private String propertyLocation;
    private String street;
    private String locality;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String commune;

    private String village;

    private String postalCode;
    private String province;
    private String country;
    private String passportNo;
    private String email;
    private String phoneNo;
    private Integer arrivalPort;
    @Column(name = "port_address")
    private Integer portAddress;
    @Transient
    private String PortAddressName;
    @Transient
    private String arrivalPortName;
    @Transient
    private String asTypeName;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedOn;
    private String phoneOtp;
    private String emailOtp;
    private String displayName;
    private String employeeId;
    private Integer natureOfEmployment;
    @Transient
    private String natureOfEmploymentInterp;
    private String designation;
    private String authorityName;
    private String authorityEmail;
    private String authorityPhoneNo;
    private String operatorTypeName;
    private Integer operatorTypeId;

    private String idCardFilename;
    
    @Column(name = "vat_file_name")
    private String vatFilename;

    @Transient
    private String nidFilePath;
    @Transient
    private String photoFilePath;
    @Transient
    private String idCardFilePath;
    @Transient
    private String vatFilePath;

    @Transient
    private String username;
    
    @Transient
    private long[] roles;

    @Transient
    private long userTypeId;

    @Transient
    private String usertypeName;
    @Transient
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String source;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    
    @Column(name = "source_user_name")
    private String sourceUsername;
    @Transient
    private String userAgent;
    @Transient
    private String publicIp;

    @Transient
    private String browser;

    @Transient
    private String userLanguage;

    @Transient
    private String approvedBy;

    public long[] getRoles() {
        return roles;
    }

    public void setRoles(long[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        UserProfile.serialVersionUID = serialVersionUID;
    }

    public long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUsertypeName() {
        return usertypeName;
    }

    public void setUsertypeName(String usertypeName) {
        this.usertypeName = usertypeName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVatStatus() {
        return vatStatus;
    }

    public void setVatStatus(Integer vatStatus) {
        this.vatStatus = vatStatus;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getPhoneOtp() {
        return phoneOtp;
    }

    public void setPhoneOtp(String phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getNatureOfEmployment() {
        return natureOfEmployment;
    }

    public void setNatureOfEmployment(Integer natureOfEmployment) {
        this.natureOfEmployment = natureOfEmployment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityEmail() {
        return authorityEmail;
    }

    public void setAuthorityEmail(String authorityEmail) {
        this.authorityEmail = authorityEmail;
    }

    public String getAuthorityPhoneNo() {
        return authorityPhoneNo;
    }

    public void setAuthorityPhoneNo(String authorityPhoneNo) {
        this.authorityPhoneNo = authorityPhoneNo;
    }

    public String getOperatorTypeName() {
        return operatorTypeName;
    }

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }

    public Integer getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(Integer operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    public String getIdCardFilename() {
        return idCardFilename;
    }

    public void setIdCardFilename(String idCardFilename) {
        this.idCardFilename = idCardFilename;
    }

    public String getAsTypeName() {
        return asTypeName;
    }

    public void setAsTypeName(String asTypeName) {
        this.asTypeName = asTypeName;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Integer arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public String getArrivalPortName() {
        return arrivalPortName;
    }

    public void setArrivalPortName(String arrivalPortName) {
        this.arrivalPortName = arrivalPortName;
    }

    public void setNidFilePath(String nidFilePath) {
        this.nidFilePath = nidFilePath;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getIdCardFilePath() {
        return idCardFilePath;
    }

    public void setIdCardFilePath(String idCardFilePath) {
        this.idCardFilePath = idCardFilePath;
    }

    public String getVatFilename() {
        return vatFilename;
    }

    public void setVatFilename(String vatFilename) {
        this.vatFilename = vatFilename;
    }

    public String getVatFilePath() {
        return vatFilePath;
    }

    public void setVatFilePath(String vatFilePath) {
        this.vatFilePath = vatFilePath;
    }

    public String getNidFilePath() {
        return nidFilePath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public Integer getPortAddress() {
        return portAddress;
    }

    public void setPortAddress(Integer portAddress) {
        this.portAddress = portAddress;
    }

    public String getPortAddressName() {
        return PortAddressName;
    }

    public void setPortAddressName(String portAddressName) {
        PortAddressName = portAddressName;
    }

    public String getNatureOfEmploymentInterp() {
        return natureOfEmploymentInterp;
    }

    public void setNatureOfEmploymentInterp(String natureOfEmploymentInterp) {
        this.natureOfEmploymentInterp = natureOfEmploymentInterp;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserProfile [id=");
        builder.append(id);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", middleName=");
        builder.append(middleName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", companyName=");
        builder.append(companyName);
        builder.append(", type=");
        builder.append(type);
        builder.append(", vatStatus=");
        builder.append(vatStatus);
        builder.append(", vatNo=");
        builder.append(vatNo);
        builder.append(", propertyLocation=");
        builder.append(propertyLocation);
        builder.append(", street=");
        builder.append(street);
        builder.append(", locality=");
        builder.append(locality);
        builder.append(", district=");
        builder.append(district);
        builder.append(", commune=");
        builder.append(commune);
        builder.append(", village=");
        builder.append(village);
        builder.append(", postalCode=");
        builder.append(postalCode);
        builder.append(", province=");
        builder.append(province);
        builder.append(", country=");
        builder.append(country);
        builder.append(", passportNo=");
        builder.append(passportNo);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phoneNo=");
        builder.append(phoneNo);
        builder.append(", arrivalPort=");
        builder.append(arrivalPort);
        builder.append(", portAddress=");
        builder.append(portAddress);
        builder.append(", PortAddressName=");
        builder.append(PortAddressName);
        builder.append(", arrivalPortName=");
        builder.append(arrivalPortName);
        builder.append(", asTypeName=");
        builder.append(asTypeName);
        builder.append(", createdOn=");
        builder.append(createdOn);
        builder.append(", modifiedOn=");
        builder.append(modifiedOn);
        builder.append(", phoneOtp=");
        builder.append(phoneOtp);
        builder.append(", emailOtp=");
        builder.append(emailOtp);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", employeeId=");
        builder.append(employeeId);
        builder.append(", natureOfEmployment=");
        builder.append(natureOfEmployment);
        builder.append(", natureOfEmploymentInterp=");
        builder.append(natureOfEmploymentInterp);
        builder.append(", designation=");
        builder.append(designation);
        builder.append(", authorityName=");
        builder.append(authorityName);
        builder.append(", authorityEmail=");
        builder.append(authorityEmail);
        builder.append(", authorityPhoneNo=");
        builder.append(authorityPhoneNo);
        builder.append(", operatorTypeName=");
        builder.append(operatorTypeName);
        builder.append(", operatorTypeId=");
        builder.append(operatorTypeId);
        builder.append(", idCardFilename=");
        builder.append(idCardFilename);
        builder.append(", vatFilename=");
        builder.append(vatFilename);
        builder.append(", nidFilePath=");
        builder.append(nidFilePath);
        builder.append(", photoFilePath=");
        builder.append(photoFilePath);
        builder.append(", idCardFilePath=");
        builder.append(idCardFilePath);
        builder.append(", vatFilePath=");
        builder.append(vatFilePath);
        builder.append(", username=");
        builder.append(username);
        builder.append(", roles=");
        builder.append(Arrays.toString(roles));
        builder.append(", userTypeId=");
        builder.append(userTypeId);
        builder.append(", usertypeName=");
        builder.append(usertypeName);
        builder.append(", password=");
        builder.append(password);
        builder.append(", user=");
        builder.append(user);
        builder.append(", source=");
        builder.append(source);
        builder.append(", expiryDate=");
        builder.append(expiryDate);
        builder.append(", sourceUsername=");
        builder.append(sourceUsername);
        builder.append(", userAgent=");
        builder.append(userAgent);
        builder.append(", publicIp=");
        builder.append(publicIp);
        builder.append(", browser=");
        builder.append(browser);
        builder.append(", userLanguage=");
        builder.append(userLanguage);
        builder.append(", approvedBy=");
        builder.append(approvedBy);
        builder.append("]");
        return builder.toString();
    }

    public UserProfile(String firstName, String middleName, String lastName, String propertyLocation, String street,
            String locality, @NotNull String district, @NotNull String commune, @NotNull String village,
            String postalCode, String province, String country, String email, String phoneNo) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.propertyLocation = propertyLocation;
        this.street = street;
        this.locality = locality;
        this.district = district;
        this.commune = commune;
        this.village = village;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public UserProfile() {
        super();
    }

    public UserProfile(String firstName, String middleName, String lastName, String email, String phoneNo) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public UserProfile(String firstName, String middleName, String lastName, String email, String phoneNo, String displayName) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.displayName = displayName;
    }

}