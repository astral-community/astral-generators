# Astral Generators Recipes

### Assembler

```json
{
  "type": "astralgenerators:assembler",
  "inputs": [
    {
      "item": "minecraft:stone",
      "count": 2
    },
    {
      "item": "minecraft:dirt"
    }
  ],
  "output": {
    "item": "custommachinery:custom_machine_item",
    "count": 1,
    "nbt": {
      "machine": "astralgenerators:steam_turbine_controller"
    }
  }
}
```

### Amalgamation Matrix

```json
{
  "type": "custommachinery:custom_machine",
  "machine": "astralgenerators:amalgamation_matrix_controller",
  "time": 10,
  "requirements": [
    {
      "type": "custommachinery:item",
      "item": "minecraft:stone",
      "mode": "input",
      "amount": 1
    },
    {
      "type": "custommachinery:item",
      "item": "minecraft:cobblestone",
      "mode": "input",
      "amount": 1
    },
    {
      "type": "custommachinery:item",
      "item": "minecraft:iron_ingot",
      "mode": "input",
      "amount": 1
    },
    {
      "type": "custommachinery:item",
      "item": "minecraft:gold_ingot",
      "mode": "input",
      "amount": 1
    },
    {
      "type": "custommachinery:item",
      "item": "minecraft:diamond",
      "mode": "output",
      "amount": 1
    },
    {
      "type": "custommachinery:energy",
      "mode": "input",
      "amount": 100000
    },
    {
      "type": "custommachinery:structure",
      "keys": {
        "a": "astralgenerators:matrix_casing",
        "b": "createastral:ultramatter",
        "c": "astralgenerators:high_magnetic_coil"
      },
      "pattern": [
        [
          " abbba ",
          "a     a",
          "b     b",
          "b     b",
          "b     b",
          "a     a",
          " abbba "
        ],
        [
          "a     a",
          " baaab ",
          " a   a ",
          " a   a ",
          " a   a ",
          " bamab ",
          "a     a"
        ],
        [
          "c     c",
          " b   b ",
          "       ",
          "       ",
          "       ",
          " b   b ",
          "c     c"
        ],
        [
          "c     c",
          "       ",
          "       ",
          "       ",
          "       ",
          "       ",
          "c     c"
        ],
        [
          "c     c",
          " b   b ",
          "       ",
          "       ",
          "       ",
          " b   b ",
          "c     c"
        ],
        [
          "a     a",
          " baaab ",
          " a   a ",
          " a   a ",
          " a   a ",
          " baaab ",
          "a     a"
        ],
        [
          " abbba ",
          "a     a",
          "b     b",
          "b     b",
          "b     b",
          "a     a",
          " abbba "
        ]
      ]
    }
  ]
}
```

### Boiler
```json
{
  "type": "custommachinery:custom_machine",
  "machine": "astralgenerators:solid_fuel_boiler_controller",
  "time": 10,
  "requirements": [
    {
      "type": "custommachinery:fuel"
    },
    {
      "type": "custommachinery:fluid",
      "mode": "input",
      "fluid": "minecraft:water",
      "amount": 100800
    },
    {
      "type": "custommachinery:fluid",
      "fluid": "astralgenerators:steam",
      "amount": 201600,
      "mode": "output"
    },
    {
      "type": "custommachinery:structure",
      "keys": {
        "a": "astralgenerators:boiler_casing",
        "b": "astralgenerators:pipe_casing"
      },
      "pattern": [
        [
          "aaa",
          "aaa",
          "aaa",
          " m "
        ],
        [
          "aaa",
          "aba",
          "aba",
          "   "
        ],
        [
          "aaa",
          "aaa",
          "aaa",
          "   "
        ]
      ]
    }
  ]
}
```
```json
{
  "type": "custommachinery:custom_machine",
  "machine": "astralgenerators:liquid_fuel_boiler",
  "time": 10,
  "requirements": [
    {
      "type": "custommachinery:fluid",
      "mode": "input",
      "fluid": "minecraft:lava",
      "amount": 100800
    },
    {
      "type": "custommachinery:fluid",
      "mode": "input",
      "fluid": "minecraft:water",
      "amount": 100800
    },
    {
      "type": "custommachinery:fluid",
      "fluid": "astralgenerators:steam",
      "amount": 201600,
      "mode": "output"
    },
    {
      "type": "custommachinery:structure",
      "keys": {
        "a": "astralgenerators:boiler_casing",
        "b": "astralgenerators:pipe_casing"
      },
      "pattern": [
        [
          "aaa",
          "aaa",
          "aaa",
          " m "
        ],
        [
          "aaa",
          "aba",
          "aba",
          "   "
        ],
        [
          "aaa",
          "aaa",
          "aaa",
          "   "
        ]
      ]
    }
  ]
}
```

### Steam Turbine

```json
{
  "type": "custommachinery:custom_machine",
  "machine": "astralgenerators:steam_turbine_controller",
  "time": 10,
  "requirements": [
    {
      "type": "custommachinery:fluid",
      "fluid": "astralgenerators:steam",
      "amount": 84000,
      "mode": "input"
    },
    {
      "type": "custommachinery:energy",
      "amount": 15000,
      "mode": "output"
    },
    {
      "type": "custommachinery:structure",
      "keys": {
        "a": "astralgenerators:steam_turbine_casing",
        "b": "astralgenerators:pipe_casing",
        "c": "astralgenerators:steam_turbine_vent"
      },
      "pattern": [
        [
          "aaaa",
          "aaaa",
          "aaaa",
          " m  "
        ],
        [
          "aaaa",
          "abba",
          "abaa",
          " b  "
        ],
        [
          "aaaa",
          "acca",
          "aaaa",
          "    "
        ]
      ]
    }
  ]
}
```
