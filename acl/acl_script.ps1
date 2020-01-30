# Ovu skriptu treba pokretati kao Administrator

$group = "SepAuthenticationUsersGroup"
$groupUsers = "Users"
$user = "auth_user"

function Strings-Contains {
 foreach($name in $args[0]) {
	if($name -Match $args[1]) {
		return $true
	}
 }
 
 return $false;
}

$GroupExists = Get-LocalGroup -Name $group
if ($GroupExists -eq $NULL) {
	New-LocalGroup -Name $group
}


# Samo proveravamo da li vec postoji ovaj user 
$userExist =(Get-LocalUser).Name -Contains $user
if(-Not $userExist) {
	$SecureStringPassword = ConvertTo-SecureString $user -AsPlainText -Force
    New-LocalUser -Name $user -Password $SecureStringPassword
	Write-Host "Kreiran novi lokalni User"
}



$userInGroup = Strings-Contains (Get-LocalGroupMember $group).Name $user
if (-Not $userInGroup) {
	Add-LocalGroupMember -Group $group -Member $user
}


#authentication service user
#trebaju nam seledece permisije
#1. truststore sme samo da cita 

$path_auth_ts = "..\AuthenticationService\src\main\resources\truststore"
$acl = Get-Acl $path_auth_ts
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Read
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_ts -AclObject $acl

#2. keystore sme da modifikuje i da cita, sva prava ima 

$path_auth_ks = "..\AuthenticationService\src\main\resources\keystore-auth"
$acl = Get-Acl $path_auth_ks
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_ks -AclObject $acl

#3. data sql ima punu kontrolu nad ovim fajlom 
$path_auth_db = "..\AuthenticationService\src\main\resources\data.sql"
$acl = Get-Acl $path_auth_db
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_db -AclObject $acl


#4. ima pravo da izvrsava java fajl 
$path_authentication_service = "..\AuthenticationService\src\main\java\rs\ac\uns\ftn\authentication_service\AuthenticationServiceApplication.java"
$acl = Get-Acl $path_authentication_service
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::ExecuteFile
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_authentication_service -AclObject $acl

#5. nad logovima ima sva prava 
$path_auth_log = "..\AuthenticationService\src\main\resources\log4j2-spring.xml"
$acl = Get-Acl $path_auth_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_log -AclObject $acl

$path_auth_log = "..\AuthenticationService\logs\spring-boot-logger-log4j2-debug.log"
$acl = Get-Acl $path_auth_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_log -AclObject $acl

$path_auth_log = "..\AuthenticationService\logs\spring-boot-logger-log4j2-error.log"
$acl = Get-Acl $path_auth_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_log -AclObject $acl

$path_auth_log = "..\AuthenticationService\logs\spring-boot-logger-log4j2-fatal.log"
$acl = Get-Acl $path_auth_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_log -AclObject $acl

$path_auth_log = "..\AuthenticationService\logs\spring-boot-logger-log4j2-info.log"
$acl = Get-Acl $path_auth_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_log -AclObject $acl

#6. sme da pise u truestore od drugih app
#banka
$path_bank_trust = "..\bank_service\src\main\resources\truststore"
$acl = Get-Acl $path_bank_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_trust -AclObject $acl
#paypal
$path_paypal_trust = "..\PaypalService\src\main\resources\truststore"
$acl = Get-Acl $path_paypal_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_paypal_trust -AclObject $acl

#zull
$path_zull_trust = "..\ZuulGateway\src\main\resources\truststore1"
$acl = Get-Acl $path_zull_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_zull_trust -AclObject $acl

#############################################################

#korisnici koji rade sa aplikacijom za banku
$group = "SepBankUsersGroup"
$groupUsers = "Users"
$user = "bank_user"

function Strings-Contains {
 foreach($name in $args[0]) {
	if($name -Match $args[1]) {
		return $true
	}
 }
 
 return $false;
}

$GroupExists = Get-LocalGroup -Name $group
if ($GroupExists -eq $NULL) {
	New-LocalGroup -Name $group
}


# Samo proveravamo da li vec postoji ovaj user 
$userExist =(Get-LocalUser).Name -Contains $user
if(-Not $userExist) {
	$SecureStringPassword = ConvertTo-SecureString $user -AsPlainText -Force
    New-LocalUser -Name $user -Password $SecureStringPassword
	Write-Host "Kreiran novi lokalni User"
}



$userInGroup = Strings-Contains (Get-LocalGroupMember $group).Name $user
if (-Not $userInGroup) {
	Add-LocalGroupMember -Group $group -Member $user
}

#1. truststore sme samo da cita 

$path_bank_ts = "..\bank_service\src\main\resources\truststore"
$acl = Get-Acl $path_bank_ts
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Read
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_ts -AclObject $acl

#2. keystore sme da modifikuje i da cita, sva prava ima 

$path_bank_ks = "..\bank_service\src\main\resources\keystore"
$acl = Get-Acl $path_bank_ks
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_ks -AclObject $acl

#3. data sql ima punu kontrolu nad ovim fajlom 
$path_bank_db = "..\bank_service\src\main\resources\data.sql"
$acl = Get-Acl $path_bank_db
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_db -AclObject $acl


#4. ima pravo da izvrsava java fajl 
$path_bank_service = "..\bank_service\src\main\java\rs\ac\uns\ftn\bank_service\BankServiceApplication.java"
$acl = Get-Acl $path_bank_service
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::ExecuteFile
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_service -AclObject $acl

#5. nad logovima ima sva prava 
$path_bank_log = "..\bank_service\src\main\resources\log4j2-spring.xml"
$acl = Get-Acl $path_bank_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_log -AclObject $acl

#6. sme da pise u truestore od drugih app
#banka
$path_authentication_trust = "..\AuthenticationService\src\main\resources\truststore"
$acl = Get-Acl $path_authentication_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_authentication_trust -AclObject $acl
#paypal
$path_paypal_trust = "..\PaypalService\src\main\resources\truststore"
$acl = Get-Acl $path_paypal_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_paypal_trust -AclObject $acl

#zull
$path_zull_trust = "..\ZuulGateway\src\main\resources\truststore1"
$acl = Get-Acl $path_zull_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_zull_trust -AclObject $acl

####################################################################
#korisnici paypal app

$group = "SepPayPalUsersGroup"
$groupUsers = "Users"
$user = "paypal_user"

function Strings-Contains {
 foreach($name in $args[0]) {
	if($name -Match $args[1]) {
		return $true
	}
 }
 
 return $false;
}

$GroupExists = Get-LocalGroup -Name $group
if ($GroupExists -eq $NULL) {
	New-LocalGroup -Name $group
}


# Samo proveravamo da li vec postoji ovaj user 
$userExist =(Get-LocalUser).Name -Contains $user
if(-Not $userExist) {
	$SecureStringPassword = ConvertTo-SecureString $user -AsPlainText -Force
    New-LocalUser -Name $user -Password $SecureStringPassword
	Write-Host "Kreiran novi lokalni User"
}



$userInGroup = Strings-Contains (Get-LocalGroupMember $group).Name $user
if (-Not $userInGroup) {
	Add-LocalGroupMember -Group $group -Member $user
}

#1. truststore sme samo da cita 

$path_pp_ts = "..\PaypalService\src\main\resources\truststore"
$acl = Get-Acl $path_pp_ts
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Read
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_ts -AclObject $acl

#2. keystore sme da modifikuje i da cita, sva prava ima 

$path_pp_ks = "..\PaypalService\src\main\resources\keystore-pp"
$acl = Get-Acl $path_pp_ks
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_ks -AclObject $acl

#3. data sql ima punu kontrolu nad ovim fajlom 
$path_pp_db = "..\PaypalService\src\main\resources\data.sql"
$acl = Get-Acl $path_pp_db
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_db -AclObject $acl


#4. ima pravo da izvrsava java fajl 
$path_pp_service = "..\PaypalService\src\main\java\rs\ac\uns\ftn\paypal_service\PaypalServiceApplication.java"
$acl = Get-Acl $path_pp_service
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::ExecuteFile
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_service -AclObject $acl

#5. nad logovima ima sva prava 
$path_pp_log = "..\PaypalService\src\main\resources\log4j2-spring.xml"
$acl = Get-Acl $path_pp_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_log -AclObject $acl

#6. sme da pise u truestore od drugih app
#banka
$path_authentication_trust = "..\AuthenticationService\src\main\resources\truststore"
$acl = Get-Acl $path_authentication_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_authentication_trust -AclObject $acl
#paypal
$path_bank_trust = "..\bank_service\src\main\resources\truststore"
$acl = Get-Acl $path_bank_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_trust -AclObject $acl

#zull
$path_zull_trust = "..\ZuulGateway\src\main\resources\truststore1"
$acl = Get-Acl $path_zull_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_zull_trust -AclObject $acl

##################################################
##bitcoin korisnici 
$group = "SepBitcoinUsersGroup"
$groupUsers = "Users"
$user = "bitcoin_user"

function Strings-Contains {
 foreach($name in $args[0]) {
	if($name -Match $args[1]) {
		return $true
	}
 }
 
 return $false;
}

$GroupExists = Get-LocalGroup -Name $group
if ($GroupExists -eq $NULL) {
	New-LocalGroup -Name $group
}


# Samo proveravamo da li vec postoji ovaj user 
$userExist =(Get-LocalUser).Name -Contains $user
if(-Not $userExist) {
	$SecureStringPassword = ConvertTo-SecureString $user -AsPlainText -Force
    New-LocalUser -Name $user -Password $SecureStringPassword
	Write-Host "Kreiran novi lokalni User"
}



$userInGroup = Strings-Contains (Get-LocalGroupMember $group).Name $user
if (-Not $userInGroup) {
	Add-LocalGroupMember -Group $group -Member $user
}

#1. truststore sme samo da cita 


#2. keystore sme da modifikuje i da cita, sva prava ima 

$path_bitcoin_ks = "..\bitcoin\src\main\resources\keystore-btc"
$acl = Get-Acl $path_bitcoin_ks
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bitcoin_ks -AclObject $acl

#3. data sql ima punu kontrolu nad ovim fajlom 
$path_bitcoin_db = "..\bitcoin\src\main\resources\data.sql"
$acl = Get-Acl $path_bitcoin_db
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bitcoin_db -AclObject $acl


#4. ima pravo da izvrsava java fajl 
$path_bitcoin_service = "..\bitcoin\src\main\java\com\bitcoin\bitcoin\BitcoinApplication.java"
$acl = Get-Acl $path_bitcoin_service
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::ExecuteFile
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bitcoin_service -AclObject $acl

#5. nad logovima ima sva prava 
$path_bitcoin_log = "..\bitcoin\src\main\resources\log4j2-spring.xml"
$acl = Get-Acl $path_bitcoin_log
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::FullControl
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bitcoin_log -AclObject $acl

#6. sme da pise u truestore od drugih app
#banka
$path_authentication_trust = "..\AuthenticationService\src\main\resources\truststore"
$acl = Get-Acl $path_authentication_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_authentication_trust -AclObject $acl
#paypal
$path_bank_trust = "..\bank_service\src\main\resources\truststore"
$acl = Get-Acl $path_bank_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_trust -AclObject $acl

#zull
$path_zull_trust = "..\ZuulGateway\src\main\resources\truststore1"
$acl = Get-Acl $path_zull_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_zull_trust -AclObject $acl

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_authentication_trust -AclObject $acl
#paypal
$path_paypal_trust = "..\PaypalService\src\main\resources\truststore"
$acl = Get-Acl $path_paypal_trust
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Write
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)

$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_paypal_trust -AclObject $acl

