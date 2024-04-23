db = db.getSiblingDB('bfi')
db.createUser({user: 'root', pwd: 'example', roles: [{role: 'dbOwner', db: 'bfi',},],});

db.location.insertMany(
    [
        {
            "_id": "test",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string2",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string3",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string4",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string5",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string6",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string7",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string8",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string9",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string10",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string11",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string12",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string13",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string14",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string15",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string16",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string17",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string18",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string19",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        },
        {
            "_id": "string20",
            "_class": "com.example.bfi.domain.entity.Location",
            "address": "string",
            "charging_when_closed": true,
            "city": "string",
            "country": "str",
            "country_code": "st",
            "evse": [{"_id":"string", "uid":"string", "evse_id":"string", "status":"AVAILABLE", "directions":"string", "last_updated": new ISODate("2024-04-23T00:34:52.661Z"), "connector":[{_id:"string", max_voltage:0, max_amperage:0, max_electric_power:0, tariff_ids:"string", last_updated: new ISODate("2024-04-23T00:34:52.661Z")}]}],
            "last_updated": new ISODate("2024-04-23T00:34:52.661Z"),
            "name": "string",
            "party_id": "string2",
            "postal_code": "string",
            "publish": true,
            "state": "string",
            "time_zone": "string"
        }
    ]
);
